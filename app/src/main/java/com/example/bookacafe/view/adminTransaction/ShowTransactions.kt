package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bookacafe.R;
import com.example.bookacafe.controller.AdminControllers;
import com.google.android.material.tabs.TabLayout;

class ShowTransactions: AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_transaction);

        tabLayout = findViewById(R.id.tabTransactionLayout);
        viewPager = findViewById(R.id.viewTransactionPage);

        var text: TextView = findViewById(R.id.allIncome);
        text.setText("Total Income: Rp" + AdminControllers().getTotalIncome());

//        tabLayout.addTab(tabLayout.newTab().setText("Seat"));
//        tabLayout.addTab(tabLayout.newTab().setText("Food"));
//        tabLayout.addTab(tabLayout.newTab().setText("Beverage"));
//        tabLayout.addTab(tabLayout.newTab().setText("Book"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        val adapter: AdminTransactionAdapter = AdminTransactionAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}