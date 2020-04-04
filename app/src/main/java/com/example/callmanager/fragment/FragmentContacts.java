package com.example.callmanager.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callmanager.Adapter.ContactsAdapter;
import com.example.callmanager.ContactDetailsActivity;
import com.example.callmanager.R;
import com.example.callmanager.manager.ContactsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentContacts extends Fragment implements ContactsAdapter.OnClick {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private RecyclerView recyclerView;
    private View v;
    private ContactsAdapter adapter;
    FloatingActionButton fab;
    List<ContactsManager.ModelContactsShort> list = new ArrayList<>();
    ContactsManager contactsManager = ContactsManager.getInstance();

    public FragmentContacts() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_contacts, container, false);
        recyclerView = v.findViewById(R.id.rv_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactsAdapter(getContext(), list,this);
        fab=v.findViewById(R.id.floatingActionButton);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsManager.addContact();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (checkPermission(MY_PERMISSIONS_REQUEST_READ_CONTACTS)) {
            list.clear();
            list.addAll(contactsManager.getShortList());
            adapter.notifyDataSetChanged();
        } else {
            askPermission(MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    private boolean checkPermission(int requestCode) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                    return true;
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                    return true;
                break;
            }
        }
        return false;
    }


    private void askPermission(int requestCode) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (checkPermission(MY_PERMISSIONS_REQUEST_READ_CONTACTS)) {
                    list.clear();
                    list.addAll(contactsManager.getShortList());
                    adapter.notifyDataSetChanged();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS:{
                if (checkPermission(MY_PERMISSIONS_REQUEST_WRITE_CONTACTS)) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    @Override
    public void favButtonClick(String id) {

    }

    @Override
    public void itemClick(String id) {
        Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
        intent.putExtra("contacts_id",id);
        startActivity(intent);
    }
}
