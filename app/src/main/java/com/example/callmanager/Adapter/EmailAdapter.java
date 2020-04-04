package com.example.callmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.callmanager.R;
import com.example.callmanager.manager.ContactsManager;

import java.util.List;

public class EmailAdapter extends BaseAdapter {
    Context context;
    List<ContactsManager.Email> list;
    public EmailAdapter(Context context, List<ContactsManager.Email> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_email, parent, false);
        }
        ContactsManager.Email currentItem = (ContactsManager.Email) getItem(position);
        TextView type = convertView.findViewById(R.id.email_type);
        TextView number = convertView.findViewById(R.id.email);
        type.setText(((ContactsManager.Email) getItem(position)).getType());
        number.setText(((ContactsManager.Email) getItem(position)).getEmail_address());
        return convertView;
    }
}
