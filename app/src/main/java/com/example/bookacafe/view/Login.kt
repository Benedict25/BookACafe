package com.example.bookacafe.view

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser
import com.example.bookacafe.controller.LoginControllers
import com.example.bookacafe.controller.RegisterControllers
import com.example.bookacafe.controller.UserControllers
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class Login : AppCompatActivity() {
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googleButton: ImageView
    private lateinit var loginButton: Button
    private lateinit var inputEmailET: EditText
    private lateinit var inputPasswordET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        // Manual Login
        loginButton = findViewById(R.id.login_button)
        inputEmailET = findViewById(R.id.login_email)
        inputPasswordET = findViewById(R.id.login_password)

        loginButton.setOnClickListener {
            val inputEmail: String = inputEmailET.text.toString()
            val inputPassword: String = inputPasswordET.text.toString()

            val control = LoginControllers()
            val successLogin: Boolean = control.getLoginData(inputEmail, inputPassword)

            if (successLogin){
                Toast.makeText(applicationContext, "Welcome!", Toast.LENGTH_SHORT).show()
                navigateToHomeScreen()
            } else {
                Toast.makeText(applicationContext, "No Account Found!", Toast.LENGTH_SHORT).show()
            }
        }

        // To Register
        signupTextSpan() // Partial clickable textview

        // Google Login
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this,gso)
        googleButton = findViewById(R.id.login_google)

        // Kalau udh login, kluar app tapi ga sign out, pas masuk lgi jdnya langsung login otomatis
        var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToHomeScreen()
        }

        googleButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        var signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000){
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)

                // Get logged in user's data
                var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

                if (acct != null){
                    val activeEmail = acct.email.toString()
                    val control = RegisterControllers()
                    val accountExists: Boolean = control.checkAccountExistence(activeEmail)
                    // Check kalau user google sudah terdaftar di database = langsung ke home, kalau belum = lengkapin data
                    if (accountExists) {
                        val control = UserControllers()
                        control.setSingletonGoogle(activeEmail)
                        navigateToHomeScreen()
                    }else {
                        navigateToCompleteProfile()
                    }
                }

            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Google Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signupTextSpan() {
        val signupText: TextView = findViewById(R.id.login_description)
        val spannableString = SpannableString(signupText.text.toString())

        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@Login, Register::class.java)
                startActivity(intent)
            }
        }

        spannableString.setSpan(clickableSpan, 25, 36, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        signupText.text = spannableString
        signupText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToHomeScreen(){
        finish()
        lateinit var intent: Intent
        if (ActiveUser.getType() == "MEMBER") {
            intent = Intent(this@Login, HomePage::class.java)
        } else if (ActiveUser.getType() == "ADMIN") {
            intent = Intent(this@Login, AdminActivity::class.java)
        } else if (ActiveUser.getType() == "CASHIER") {
            intent = Intent(this@Login, CashierActivity::class.java)
        }
        startActivity(intent)
    }

    private fun navigateToCompleteProfile() {
        finish()
        val intent = Intent(this@Login, CompleteProfile::class.java)
        startActivity(intent)
    }
}