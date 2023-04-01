package com.example.bookacafe.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser
import com.example.bookacafe.view.adminTransaction.ShowTransactions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class Login : AppCompatActivity() {

    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var googleButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this,gso)
        googleButton = findViewById(R.id.login_google)

        val signupButton: TextView = findViewById(R.id.login_description)

        signupButton.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }

        // Kalau udh login, kluar app tapi ga sign out, pas masuk lgi jdnya langsung login otomatis
        var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToSecondActivity()
        }

        googleButton.setOnClickListener {
            signIn()
        }
    }

    fun signIn() {
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
                    var activeName: String = acct.displayName.toString()
                    // Save to singleton before continuing to next activity
                    ActiveUser.setName(activeName)
                }

                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun navigateToSecondActivity(){
        finish()
        //ini buat direct ke admin yang transactions.. nanti di ganti ganti lagi aja
//        val switchActivityIntent = Intent(this, ShowTransactions::class.java)
//        startActivity(switchActivityIntent)

        // ini buat ke TestLogin
//        val intent = Intent(this@Login, TestLogin::class.java)
//        startActivity(intent)

        val intent = Intent(this@Login, HomePage::class.java)
        startActivity(intent)
    }

}