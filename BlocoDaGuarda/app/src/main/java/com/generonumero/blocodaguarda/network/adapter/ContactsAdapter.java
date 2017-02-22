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
    private View.OnClickListener onClickListener;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.network_contact, parent, false);
        return new ContactViewHolder(view, getOnClickListener());
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = this.contacts.get(position);


        holder.contactLabel.setText(holder.itemView.getContext().getString(R.string.bdg_network_contact_label) + " " + (++position));

        if(contact == null) return;

        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public View.OnClickListener getOnClickListener() {
        if (onClickListener == null) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            };
        }
        return onClickListener;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {



        @Bind(R.id.bdg_network_phone)
        public EditText phone;
        @Bind(R.id.bdg_network_contact_name)
        public EditText name;
        @Bind(R.id.bdg_network_contact_address_book)
        public ImageButton addressBook;
        @Bind(R.id.bdg_network_contact_label)
        public TextView contactLabel;

        public ContactViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(onClickListener);
        }


    }

}
