package com.example.bookacafe.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookacafe.R

class MenuCart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_cart)
        supportActionBar?.hide()
    }

//    fun ShowCart() {}
}