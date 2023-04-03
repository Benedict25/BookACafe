package com.example.bookacafe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bookacafe.R

class CashierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cashier)

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = CashierHomeFragment()

        val fragment = mFragmentManager.findFragmentByTag(CashierHomeFragment::class.java.simpleName)

        if (fragment !is CashierHomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :"+ CashierHomeFragment::class.java.simpleName)
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mHomeFragment, CashierHomeFragment::class.java.simpleName)
                .commit()
        }

    }
}