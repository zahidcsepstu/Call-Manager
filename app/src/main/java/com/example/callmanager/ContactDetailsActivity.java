package com.example.callmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.callmanager.Adapter.EmailAdapter;
import com.example.callmanager.Adapter.PhoneAdapter;
import com.example.callmanager.manager.ContactsManager;

import java.util.List;

public class ContactDetailsActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private String contact_id;
    TextView name,saveLocation;
    ImageView image;
    ListView number,email;
    ContactsManager contactsManager=ContactsManager.getInstance();
    ContactsManager.ModelContacts item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        contact_id = getIntent().getStringExtra("contacts_id");
        item=contactsManager.getContact(contact_id);
        name=findViewById(R.id.name);
        saveLocation=findViewById(R.id.save_location);
        image=findViewById(R.id.large_image);
        number=findViewById(R.id.list_phone);
        email=findViewById(R.id.list_email);

        name.setText(item.getName());
        saveLocation.setText(item.getSave_location());
        Bitmap img=item.getImage();
        if(img!=null)
           image.setImageBitmap(img);
        else
            image.setImageResource(R.drawable.user_large);
        number.setAdapter(new PhoneAdapter(this,item.getNumberList()));
        email.setAdapter(new EmailAdapter(this,item.getEmailList()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                contactsManager.editContact(contact_id);
                return true;
            case R.id.delete:
                if(checkPermission(MY_PERMISSIONS_REQUEST_WRITE_CONTACTS)){
                    contactsManager.deleteContact(contact_id);
                    ContactDetailsActivity.this.finish();
                }
                else
                    askPermission(MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private boolean checkPermission(int requestCode) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                    return true;
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                    return true;
                break;
            }
        }
        return false;
    }

    private void askPermission(int requestCode) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                ActivityCompat.requestPermissions(ContactDetailsActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                ActivityCompat.requestPermissions(ContactDetailsActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (checkPermission(MY_PERMISSIONS_REQUEST_READ_CONTACTS)) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS:{
                if (checkPermission(MY_PERMISSIONS_REQUEST_WRITE_CONTACTS)) {
                    contactsManager.deleteContact(contact_id);
                    ContactDetailsActivity.this.finish();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
