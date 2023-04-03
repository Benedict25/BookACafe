package com.example.bookacafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.bookacafe.view.HomePage
import com.example.bookacafe.view.Login
import com.example.bookacafe.view.adminDisabledUser.DisabledUserMenu
import com.example.bookacafe.view.adminTransaction.ShowTransactions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_slider_item)

//        val intent = Intent(this@MainActivity, ShowTransactions::class.java)
//        startActivity(intent)

        val intent = Intent(this@MainActivity, DisabledUserMenu::class.java)
        startActivity(intent)

//        // Hide action bar yang di atas aplikasi
//        supportActionBar?.hide()
//
//        // Pindah dari splash screen ke Home (delay 2 detik)
//        Handler().postDelayed({
//            val intent = Intent(this@MainActivity, Login::class.java)
//            startActivity(intent)
//        }, 2000)
    }
}