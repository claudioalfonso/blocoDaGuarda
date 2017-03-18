package com.generonumero.blocodaguarda.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.network.model.Contact;

public class ContactView extends LinearLayout {

    private TextView phone;
    private TextView name;
    private ImageButton addressBook;
    private TextView contactLabel;
    private Contact contact;

    public ContactView(Context context) {
        super(context);

    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public ContactView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        View view = inflate(context, R.layout.network_contact, this);

        phone = (TextView) view.findViewById(R.id.bdg_network_phone);
        name = (TextView) view.findViewById(R.id.bdg_network_contact_name);
        addressBook = (ImageButton) view.findViewById(R.id.bdg_network_contact_address_book);
        contactLabel = (TextView) view.findViewById(R.id.bdg_network_contact_label);
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        this.contact.setName(this.name.getText().toString());
        this.contact.setPhone(this.phone.getText().toString());
        return this.contact;
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setContactLabel(Integer position) {
        this.contactLabel.setText(getContext().getString(R.string.bdg_network_contact_label) + " " + (position + 1));
    }

    public void setAddressBookClickListener( OnClickListener addressBookClickListener) {
        this.addressBook.setOnClickListener(addressBookClickListener);
    }
}
