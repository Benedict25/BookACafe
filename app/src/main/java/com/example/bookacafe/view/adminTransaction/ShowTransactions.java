package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bookacafe.R;
import com.google.android.material.tabs.TabLayout;

public class ShowTransactions extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_transaction);

        tabLayout = findViewById(R.id.tabTransactionLayout);
        viewPager = findViewById(R.id.viewTransactionPage);

//        tabLayout.addTab(tabLayout.newTab().setText("Seat"));
//        tabLayout.addTab(tabLayout.newTab().setText("Food"));
//        tabLayout.addTab(tabLayout.newTab().setText("Beverage"));
//        tabLayout.addTab(tabLayout.newTab().setText("Book"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        AdminTransactionAdapter adapter = new AdminTransactionAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
