package com.generonumero.blocodaguarda.network.presenter.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.generonumero.blocodaguarda.network.view.NetworkView;
import com.generonumero.blocodaguarda.permission.service.PermissionService;

import java.util.ArrayList;
import java.util.List;


public class NetworkPresenterImpl implements NetworkPresenter {

    private static final int RESULT_CODE_PERMISSION = 123;
    private static final int RESULT_CODE_PERMISSION_SMS = 124;
    private static final int RESULT_CODE_PICK = 34;
    private static final String PERMISSION = Manifest.permission.READ_CONTACTS;
    private static final String PERMISSION_SMS = Manifest.permission.SEND_SMS;
    private static final String PERMISSION_PHONESTATE = Manifest.permission.READ_PHONE_STATE;

    private NetworkView networkView;
    private NetworkRepository networkRepository;
    private PermissionService permissionService;
    private String idContactList;
    private LoginRepository loginRepository;

    public NetworkPresenterImpl(NetworkView networkView, NetworkRepository networkRepository, PermissionService permissionService, LoginRepository loginRepository) {
        this.networkView = networkView;
        this.networkRepository = networkRepository;
        this.permissionService = permissionService;
        this.loginRepository = loginRepository;
    }

    @Override
    public void loadViews() {
        networkView.OnLoadViews(networkRepository.getAllContacts());

        if (networkRepository.isFirstOpen()) {
            networkView.showPopupExplaningNetwork();
        }
    }


    @Override
    public void pickContact(Fragment fragment, String idContactList) {
        if (permissionService.hasNeedAskPermission(fragment.getContext(), PERMISSION) ||
                permissionService.hasNeedAskPermission(fragment.getContext(), PERMISSION_PHONESTATE)) {
            permissionService.askPermissionFromFragment(fragment, new String[]{PERMISSION, PERMISSION_PHONESTATE}, RESULT_CODE_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            fragment.startActivityForResult(intent, RESULT_CODE_PICK);
            this.idContactList = idContactList;
        }
    }

    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults, String permission) {
        switch (requestCode) {

            case RESULT_CODE_PERMISSION:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (permissionService.getPermissionStatus(activity, permission) == PermissionService.BLOCKED_OR_NEVER_ASKED) {
                        networkView.showAlertPermissionDisable();
                    } else {
                        networkView.showAlertPermissionDenied();
                    }
                }
                break;
        }
    }

    @Override
    public void onReceiveDataFromContact(Fragment fragment, int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CODE_PICK:
                if (resultCode == Activity.RESULT_OK) {


                    if (this.idContactList == null) {
                        this.idContactList = "0";
                    }

                    Integer id = Integer.parseInt(idContactList);
                    Contact contact = getContact(fragment, data, id);
                    if (contact != null) {
                        networkRepository.update(idContactList, contact);
                        networkView.updateList(id, contact);
                    } else {
                        networkView.showContactWithoutNumber();
                    }
                }
                break;
        }
    }

    @Override
    public void saveAllContacts(List<Contact> contacts) {
        if (contactsAreValid(contacts)) {
            networkView.goHome();
            networkRepository.saveAll(contacts);
            if (!permissionService.hasNeedAskPermission(BDGApplication.getInstance(), PERMISSION_SMS)
                    && !permissionService.hasNeedAskPermission(BDGApplication.getInstance(), PERMISSION_PHONESTATE)) {
                sendSMS(contacts);
            }
        } else {
            networkView.showMsgInvalidContacts();
        }
    }

    private boolean contactsAreValid(List<Contact> contacts) {
        int numOfValids = 0;
        for (Contact contact : contacts) {
            if (contact.isValid()) {
                numOfValids++;
            }
        }
        return numOfValids > 2;
    }

    @Override
    public void showSMSPermissionIfNeeded(Fragment fragment) {
        if (permissionService.hasNeedAskPermission(fragment.getContext(), PERMISSION_SMS)) {
            permissionService.askPermissionFromFragment(fragment, new String[]{PERMISSION_SMS}, RESULT_CODE_PERMISSION_SMS);
        }

        networkRepository.saveFirstOpen();
    }


    private void sendSMS(List<Contact> contacts) {
        if (contacts == null)
            return;

        SmsManager smsManager = SmsManager.getDefault();
        UserProfile user = loginRepository.getUser();
        for (Contact contact : contacts) {
            if (!contact.isValid()) {
                continue;
            }
            String gender;
            if (user.getGender().equals("male")) {
                gender = "do";
            } else {
                gender = "da";
            }

            String msg = BDGApplication.getInstance().getString(R.string.bdg_alert_network_sms_message, contact.getName(), user.getName(), gender);
            String phone = contact.getPhoneFormated();

            ArrayList<String> parts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
        }
    }

    private Contact getContact(Fragment fragment, Intent data, Integer idContactList) {
        try {
            Uri contactData = data.getData();
            Cursor c = fragment.getActivity().managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String hasPhone =
                        c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = fragment.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    if (phones != null) {
                        phones.moveToFirst();

                        int nameIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        int numberIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                        if (nameIndex > -1 && numberIndex > -1) {
                            String name = phones.getString(nameIndex);
                            String cNumber = phones.getString(numberIndex);
                            return new Contact(idContactList, name, cNumber);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
