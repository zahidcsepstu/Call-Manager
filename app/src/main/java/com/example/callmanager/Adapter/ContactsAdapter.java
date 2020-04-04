package com.example.callmanager.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callmanager.R;
import com.example.callmanager.manager.ContactsManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{
    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ContactsManager.ModelContactsShort> listContacts;
    private OnClick onClick;

    public ContactsAdapter(Context mContext, List<ContactsManager.ModelContactsShort> listContacts,OnClick onClick) {
        this.mContext = mContext;
        this.listContacts = listContacts;
        this.onClick=onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(mContext);
        View view =layoutInflater.inflate(R.layout.item_contacts,parent,false);
        return new ViewHolder(view, onClick);
    }



    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        TextView name;
        CircleImageView image=holder.image;
        name= holder.name;
        name.setText(listContacts.get(position).getName());
        Bitmap img=listContacts.get(position).getSmallImage();
        if(img!=null)
            image.setImageBitmap(img);
        else
            image.setImageResource(R.drawable.user);

    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        CircleImageView image;
        Button fav_button;
        OnClick onClick;

        public ViewHolder(@NonNull View itemView, OnClick onClick) {
            super(itemView);
            name=itemView.findViewById(R.id.contact_name);
            image=itemView.findViewById(R.id.contact_image);
            fav_button=itemView.findViewById(R.id.contact_fav_button);
            this.onClick=onClick;
            itemView.setOnClickListener(this);
            fav_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.contact_fav_button)
                onClick.favButtonClick(listContacts.get(getAdapterPosition()).getContact_id());
            else
                onClick.itemClick(listContacts.get(getAdapterPosition()).getContact_id());
        }
    }

    public interface OnClick{
        void favButtonClick(String id);
        void itemClick(String id);
    }
}