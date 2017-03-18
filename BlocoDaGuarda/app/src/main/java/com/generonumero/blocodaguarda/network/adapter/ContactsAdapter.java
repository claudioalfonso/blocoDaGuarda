package com.generonumero.blocodaguarda.network.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.network.model.Contact;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {


    private List<Contact> contacts;
    private PickContacts pickContacts;

    public ContactsAdapter(List<Contact> contacts, PickContacts pickContacts) {
        this.contacts = contacts;
        this.pickContacts = pickContacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.network_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        final Contact contact = this.contacts.get(position);


        holder.contactLabel.setText(holder.itemView.getContext().getString(R.string.bdg_network_contact_label) + " " + (position + 1));

        if (contact == null) return;

        holder.name.setText(contact.getName());
        holder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact.setName(editable.toString());
            }
        });
        holder.phone.setText(contact.getPhone());
        holder.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact.setPhone(editable.toString());
            }
        });

        holder.addressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContacts.onClickPickContacts(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void updateContact(int position, Contact contact) {
        Contact contact1 = contacts.get(position);
        contact1.setName(contact.getName());
        contact1.setPhone(contact.getPhone());

        notifyDataSetChanged();
    }

    public List<Contact> getContacts() {

        return contacts;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.bdg_network_phone)
        EditText phone;
        @Bind(R.id.bdg_network_contact_name)
        EditText name;
        @Bind(R.id.bdg_network_contact_address_book)
        ImageButton addressBook;
        @Bind(R.id.bdg_network_contact_label)
        TextView contactLabel;

        ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
