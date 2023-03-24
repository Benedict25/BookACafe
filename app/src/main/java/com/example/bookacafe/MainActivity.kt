package com.example.bookacafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.bookacafe.view.HomePage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar yang di atas aplikasi
        supportActionBar?.hide()

        // Pindah dari splash screen ke Home (delay 2 detik)
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomePage::class.java)
            startActivity(intent)
        }, 2000)

        //testing dari feli :)
    }
}