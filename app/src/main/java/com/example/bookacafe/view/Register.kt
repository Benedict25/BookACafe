package com.example.bookacafe.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.bookacafe.R
import com.example.bookacafe.controller.RegisterControllers
import com.example.bookacafe.model.User

class Register : AppCompatActivity() {
    private lateinit var firstNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var checkPasswordET: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        val backButton: RelativeLayout = findViewById(R.id.register_back)
        backButton.setOnClickListener {
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
        }

        firstNameET = findViewById(R.id.register_first_name)
        lastNameET = findViewById(R.id.register_last_name)
        emailET = findViewById(R.id.register_email)
        passwordET = findViewById(R.id.register_password)
        checkPasswordET = findViewById(R.id.register_retype_password)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val control = RegisterControllers()
            val user = User("", firstNameET.text.toString(), lastNameET.text.toString(), emailET.text.toString(), passwordET.text.toString())

            val accountExists: Boolean = control.checkAccountExistence(user.email)

            if (accountExists) {
                Toast.makeText(applicationContext, "Email Used!", Toast.LENGTH_SHORT).show()
            } else {
                val isRegistered = control.registerUser(user, checkPasswordET.text.toString())

                if (isRegistered) {
                    Toast.makeText(applicationContext, "Registered, Please Login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Register, Login::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Password Mismatch!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}