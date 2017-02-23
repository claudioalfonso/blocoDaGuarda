package com.generonumero.blocodaguarda.network.view.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.network.adapter.ContactsAdapter;
import com.generonumero.blocodaguarda.network.adapter.PickContacts;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.view.NetworkView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkFragment extends Fragment implements NetworkView, PickContacts {


    private static final String CONTACT_BUNDLE = "contact_id";
    private static final int RESULT_CODE_PICK = 34;
    private static final int RESULT_CODE_PERMISSION = 123;
    private static final String PERMISSION = Manifest.permission.READ_CONTACTS;

    @Bind(R.id.bdg_network_recycler)
    RecyclerView mRecyclerView;

    private NetworkPresenter networkPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkPresenter = BDGApplication.getInstance().getNetworkPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.network_frag, null);
        ButterKnife.bind(this, view);
        networkPresenter.loadViews();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case RESULT_CODE_PERMISSION:
                break;
            case RESULT_CODE_PICK:
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case RESULT_CODE_PERMISSION:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (getPermissionStatus(getActivity(), PERMISSION) == BLOCKED_OR_NEVER_ASKED) {
                        Toast.makeText(getContext(), "Pedido de permissão para acessar a agenda negado. Vá em configurações e habilite-o para poder usar a agenda e pegar os seus contatos.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Precisamos dessa permissão para lhe ajudar a pegar os contatos da agenda.", Toast.LENGTH_LONG).show();
                    }

                }
                break;

        }
    }

    @OnClick(R.id.bdg_network_save)
    public void onClickSave(View v) {
        Toast.makeText(getContext(), "Salvou", Toast.LENGTH_LONG).show();
    }


    @Override
    public void OnLoadViews() {

        Contact contact = new Contact("Pedro", "21984417774");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(contactsAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClickPickContacts(int idContactList) {

        if (hasPermissionsNeeded()) {
            askPermission();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.putExtra(CONTACT_BUNDLE, idContactList);
        startActivityForResult(intent, RESULT_CODE_PICK);
    }

    private boolean hasPermissionsNeeded() {
        return ContextCompat.checkSelfPermission(getActivity(), PERMISSION) != PackageManager.PERMISSION_GRANTED;
    }

    private void askPermission() {
        requestPermissions(new String[]{PERMISSION}, RESULT_CODE_PERMISSION);
    }

    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED_OR_NEVER_ASKED = 2;


    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED_OR_NEVER_ASKED;
            }
            return DENIED;
        }
        return GRANTED;
    }

}
