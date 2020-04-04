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

public class PhoneAdapter extends BaseAdapter {
    Context context;
    List<ContactsManager.Number> list;
    public PhoneAdapter(Context context, List<ContactsManager.Number> list){
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
                    inflate(R.layout.item_phone, parent, false);
        }
        ContactsManager.Number currentItem = (ContactsManager.Number) getItem(position);
        TextView type = convertView.findViewById(R.id.phone_type);
        TextView number = convertView.findViewById(R.id.phone_number);
        type.setText(((ContactsManager.Number) getItem(position)).getType());
        number.setText(((ContactsManager.Number) getItem(position)).getNumber());
        return convertView;
    }
}
