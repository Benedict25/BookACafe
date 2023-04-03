package com.example.bookacafe.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bookacafe.R
import com.example.bookacafe.controller.LoginControllers
import com.example.bookacafe.controller.RegisterControllers
import com.example.bookacafe.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class CompleteProfile : AppCompatActivity() {

    lateinit var firstNameET: EditText
    lateinit var lastNameET: EditText
    lateinit var passwordET: EditText
    lateinit var checkPasswordET: EditText
    lateinit var completeButton: Button
    lateinit var activeEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)
        supportActionBar?.hide()

        // Get user's email from logged in Google user
        var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null){
            activeEmail = acct.email.toString()
        }

        firstNameET = findViewById(R.id.complete_first_name)
        lastNameET = findViewById(R.id.complete_last_name)
        passwordET = findViewById(R.id.complete_password)
        checkPasswordET = findViewById(R.id.complete_retype_password)
        completeButton = findViewById(R.id.complete_button)

        completeButton.setOnClickListener {
            val control = RegisterControllers()
            val user = User("", firstNameET.text.toString(), lastNameET.text.toString(), activeEmail, passwordET.text.toString())
            val isRegistered = control.registerUser(user, checkPasswordET.toString())

            if (isRegistered) {
                Toast.makeText(applicationContext, "Welcome!", Toast.LENGTH_SHORT).show()
                navigateToHomeScreen()
            } else {
                Toast.makeText(applicationContext, "Registration Error!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun navigateToHomeScreen(){
        val intent = Intent(this@CompleteProfile, TestLogin::class.java)
        startActivity(intent)
//        val intent = Intent(this@Login, HomePage::class.java)
//        startActivity(intent)
    }
}