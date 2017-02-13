package com.generonumero.blocodaguarda.network.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.network.model.Contact;

import java.util.List;

import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter {


    private List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.network_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    private static class ContactViewHolder extends RecyclerView.ViewHolder {

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
