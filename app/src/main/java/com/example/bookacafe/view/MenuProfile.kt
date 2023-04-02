package com.example.bookacafe.view

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookacafe.R

class MenuProfile: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_screen)
        supportActionBar?.hide()

        val btnHistory: Button = findViewById(R.id.btn_history)
        val btnLogout: Button = findViewById(R.id.btn_logout)

        val editFirstName: EditText = findViewById(R.id.edit_firstName)
        val editLastName: EditText = findViewById(R.id.edit_lastName)
        val editEmail: EditText = findViewById(R.id.edit_email)
        val editPassword: EditText = findViewById(R.id.edit_password)

        // Make All Fields Not Editable
        editFirstName.setText("Benedict Ivan")
        editFirstName.isEnabled = false

        editLastName.setText("Iskandar")
        editLastName.isEnabled = false

        editEmail.setText("benedictivan@iskandar.com")
        editEmail.isEnabled = false

        editPassword.setText("inipassword")
        editPassword.isEnabled = false
        editPassword.transformationMethod = PasswordTransformationMethod.getInstance()

        // Set On Click Listener for Each Button
        btnHistory.setOnClickListener(this)
        btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_history -> {
                val moveIntent = Intent(this@MenuProfile, HistoryActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_logout -> {
                val moveIntent = Intent(this@MenuProfile, Login::class.java)
                startActivity(moveIntent)

                val logout_toast = Toast.makeText(applicationContext, "Logging Out from BookACafe", Toast.LENGTH_SHORT)
                logout_toast.show()
            }
        }
    }

}