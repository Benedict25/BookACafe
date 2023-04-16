package com.example.bookacafe.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class TestLogin : AppCompatActivity() {
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var nameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var signout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_login)
        supportActionBar?.hide()

        nameTextView = findViewById(R.id.testLoginName)
        typeTextView = findViewById(R.id.testLoginType)
        signout = findViewById(R.id.testSignout)

        // Biar bisa signout
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this,gso)

        // Extract & display name & type from singleton
        nameTextView.setText(ActiveUser.getFirstName())
        typeTextView.setText(ActiveUser.getType())

        // Button signOut
        signout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        gsc.signOut().addOnCompleteListener {
            finish()
            val intent = Intent(this@TestLogin, Login::class.java)
            startActivity(intent)
        }
    }
}