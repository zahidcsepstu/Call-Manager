package com.example.callmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.example.callmanager.Adapter.ViewPageAdapter;
import com.example.callmanager.fragment.FragmentCalls;
import com.example.callmanager.fragment.FragmentContacts;
import com.example.callmanager.fragment.FragmentFav;
import com.example.callmanager.manager.ContactsManager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;
    private ContactsManager manager;
    public final int ICONS[]={R.drawable.ic_phone_24px,R.drawable.contact,R.drawable.star_filled};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager=ContactsManager.getInstance();
        manager.init(this);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewpager);
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentCalls(),"calls");
        adapter.addFragment(new FragmentContacts(),"Contacts");
        adapter.addFragment(new FragmentFav(),"Fav");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i=0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i) ;
            tab.setIcon(ICONS[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this,R.color.colorAccent);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.black);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
