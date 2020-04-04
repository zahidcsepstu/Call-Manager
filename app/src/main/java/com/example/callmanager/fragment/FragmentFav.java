package com.example.callmanager.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.example.callmanager.R;
import com.example.callmanager.manager.ContactsManager;

import java.util.ArrayList;
import java.util.List;

public class FragmentFav extends Fragment implements ContactsAdapter.OnClick {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private RecyclerView recyclerView;
    private View v;
    List<ContactsManager.ModelContactsShort> list = new ArrayList<>();
    ContactsManager contactsManager=ContactsManager.getInstance();
    public FragmentFav(){
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.frag_fav,container,false);
        recyclerView = v.findViewById(R.id.rv_fav);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        ContactsAdapter adapter = new ContactsAdapter(getContext(),list,this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(checkPermission(MY_PERMISSIONS_REQUEST_READ_CONTACTS)){
        }
        else{
            askPermission(MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    private boolean checkPermission(int requestCode) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                    return true;
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                    return true;
                break;
            }
        }
        return false;
    }


    private void askPermission(int requestCode){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS:{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    list=contactsManager.getShortList();
                } else {
                    //permission denied disable functionalities which needed this permission
                }
            }
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //permission denied disable functionalities which needed this permission
                }
            }
        }
    }

    @Override
    public void favButtonClick(String id) {

    }

    @Override
    public void itemClick(String id) {

    }
}
