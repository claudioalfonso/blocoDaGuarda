package com.generonumero.blocodaguarda.network.view.impl;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;
import com.generonumero.blocodaguarda.network.adapter.ContactsAdapter;
import com.generonumero.blocodaguarda.network.adapter.PickContacts;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.view.NetworkView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkFragment extends Fragment implements NetworkView, PickContacts {

    private static final String PERMISSION = Manifest.permission.READ_CONTACTS;

    @Bind(R.id.bdg_network_recycler)
    RecyclerView mRecyclerView;

    private NetworkPresenter networkPresenter;
    private ContactsAdapter contactsAdapter;


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
        networkPresenter.onReceiveDataFromContact(this, requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        networkPresenter.onRequestPermissionsResult(getActivity(), requestCode, grantResults, PERMISSION);
    }

    @OnClick(R.id.bdg_network_save)
    public void onClickSave(View v) {
        contactsAdapter.notifyDataSetChanged();
        List<Contact> contacts = contactsAdapter.getContacts();
        networkPresenter.saveAllContacts(contacts);

        MainActivity activity = (MainActivity) getActivity();
        activity.goToHome();
    }


    @Override
    public void OnLoadViews(List<Contact> contacts) {

        contactsAdapter = new ContactsAdapter(contacts, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(contactsAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showAlertPermissionDenied() {
        Toast.makeText(getContext(), "Precisamos dessa permissão para lhe ajudar a pegar os contatos da agenda.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertPermissionDisable() {
        Toast.makeText(getContext(), "Pedido de permissão para acessar a agenda negado. Vá em configurações e habilite-o para poder usar a agenda e pegar os seus contatos.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showContactWithoutNumber() {
        Toast.makeText(getContext(), "Esse contato não possui um telefone cadastrado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateList(int position, Contact contact) {
        contactsAdapter.updateContact(position, contact);
    }

    @Override
    public void onClickPickContacts(int idContactList) {
        networkPresenter.pickContact(this, Integer.toString(idContactList));
    }


}
