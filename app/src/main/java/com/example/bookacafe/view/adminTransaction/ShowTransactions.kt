package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;
import android.os.Handler
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bookacafe.R;
import com.example.bookacafe.controller.AdminControllers;
import com.google.android.material.tabs.TabLayout;
import java.text.DecimalFormat
import java.util.*

class ShowTransactions: AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var mAdapter: AdminTransactionAdapter

    private val mHandler = Handler()
    private val mTimer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_transaction);

        tabLayout = findViewById(R.id.tabTransactionLayout);
        viewPager = findViewById(R.id.viewTransactionPage);

        var text: TextView = findViewById(R.id.allIncome);
        text.setText("Total Income: Rp" + DecimalFormat("#,###").format(AdminControllers().getTotalIncome()));

        mAdapter = AdminTransactionAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mAdapter);

        mTimer.scheduleAtFixedRate(RefreshTask(), 0, 10000)
        tabLayout.setupWithViewPager(viewPager);
    }

    private inner class RefreshTask : TimerTask() {
        override fun run() {
            // Post ke Handler untuk memperbarui UI di Thread UI
            mHandler.post {
                // Mendapatkan posisi fragment saat ini dalam ViewPager
                val currentPosition = viewPager.currentItem
                // Menyegarkan fragment saat ini dengan menggantinya dengan fragment yang sama
                viewPager.adapter = mAdapter
                viewPager.setCurrentItem(currentPosition, false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer.cancel()
    }
}
