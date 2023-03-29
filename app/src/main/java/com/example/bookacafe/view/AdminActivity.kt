package com.example.bookacafe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bookacafe.R

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = AdminHomeFragment()

        val fragment = mFragmentManager.findFragmentByTag(AdminHomeFragment::class.java.simpleName)

        if (fragment !is AdminHomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :"+ AdminHomeFragment::class.java.simpleName)
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mHomeFragment, AdminHomeFragment::class.java.simpleName)
                .commit()
        }
    }
}