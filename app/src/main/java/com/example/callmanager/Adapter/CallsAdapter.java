package com.example.callmanager.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.callmanager.R;
import com.example.callmanager.model.ModelCalls;

import java.util.List;

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelCalls> listCalls;

    public CallsAdapter(Context mContext, List<ModelCalls> listCalls) {
        this.mContext = mContext;
        this.listCalls = listCalls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(mContext);
        View view =layoutInflater.inflate((R.layout.item_calls),parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView number,duration,date;
        number= holder.number;
        duration= holder.duration;
        date=holder.date;
        number.setText(listCalls.get(position).getName());
        duration.setText(listCalls.get(position).getDuration());
        date.setText(listCalls.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return listCalls.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        TextView number,duration,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.call_number);
            duration=itemView.findViewById(R.id.call_duration);
            date=itemView.findViewById(R.id.call_date);
        }

    }

}

