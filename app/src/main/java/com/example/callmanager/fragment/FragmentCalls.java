package com.example.callmanager.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callmanager.Adapter.CallsAdapter;
import com.example.callmanager.R;
import com.example.callmanager.model.ModelCalls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FragmentCalls extends Fragment {
    private RecyclerView recyclerView;
    private View v;
    private LinearLayoutManager linearLayoutManager;
    private CallsAdapter adapter;
    private List<ModelCalls> list = new ArrayList<>();

    public FragmentCalls() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_calls, container, false);
        recyclerView = v.findViewById(R.id.rv_calls);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CallsAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            askPermission(); ///android permission result callback
        } else {
            getCallLogs();
        }
    }

    private void getCallLogs() {
        list.clear();

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);

        int date1 = cursor.getColumnIndex(CallLog.Calls.DATE);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Date date = new Date(Long.valueOf(cursor.getString(date1)));
            String month, week, day;
            day= (String) DateFormat.format("dd",date);
            week= (String) DateFormat.format("EEEE",date);
            month= (String) DateFormat.format("MMMM",date);
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            list.add(new ModelCalls(cursor.getString(number),duration(duration),week+" "+month+" "+" "+day));
            cursor.moveToNext();

        }

    }

    private void askPermission(){
        if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALL_LOG},1);
        }
    }

    public String duration(int value){
        int m=value/60;
        int s= value%60;
        return +m+" min "+s+" sec";
    }

}
