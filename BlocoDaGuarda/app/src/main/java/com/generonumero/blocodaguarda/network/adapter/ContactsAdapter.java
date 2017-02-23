package com.generonumero.blocodaguarda.network.adapter;

import android.support.v7.widget.RecyclerView;
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
    private View.OnClickListener onClickListener;

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
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = this.contacts.get(position);


        holder.contactLabel.setText(holder.itemView.getContext().getString(R.string.bdg_network_contact_label) + " " + (++position));

        if (contact == null) return;

        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        holder.addressBook.setOnClickListener(getOnClickListener(position));

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private View.OnClickListener getOnClickListener(final int id) {
        if (onClickListener == null) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickContacts.onClickPickContacts(id);
                }
            };
        }
        return onClickListener;
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
