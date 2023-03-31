package com.example.bookacafe.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.bookacafe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class TestLogin : AppCompatActivity() {

    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var nameTextView: TextView
    lateinit var signout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_login)
        supportActionBar?.hide()

        nameTextView = findViewById(R.id.testLogin)
        signout = findViewById(R.id.testSignout)

        // Biar bisa signout
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this,gso)

        // Get data yang lagi login sekarang
        var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        // Set data ; ambil dari data yang login sekarang
        if (acct != null){
            var activeName: String = acct.displayName.toString()
            nameTextView.setText(activeName)
        }

        // Button signOut
        signout.setOnClickListener {
            signOut()
        }
    }

    fun signOut() {
        gsc.signOut().addOnCompleteListener {
            finish()
            val intent = Intent(this@TestLogin, Login::class.java)
            startActivity(intent)
        }
    }
}