package com.example.bookacafe.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser

class AdminHomeFragment :Fragment(R.layout.fragment_admin_home) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adminTextView: TextView = view.findViewById(R.id.admin_textView)
        adminTextView.text = "Welcome,\n" + ActiveUser.getFirstName() + " " + ActiveUser.getLastName()

        val btnDisableUser: Button = view.findViewById(R.id.buttonDisableUser)
        val btnShowTrans: Button = view.findViewById(R.id.buttonShowAllTransaction)
        val btnLogOut: Button = view.findViewById(R.id.buttonLogOut)
        btnDisableUser.setOnClickListener{
            val intent = Intent(context, AdminDisabledUserMenu::class.java)
            startActivity(intent)
        }
        btnShowTrans.setOnClickListener{
            val intent = Intent(context, AdminShowTransactions::class.java)
            startActivity(intent)
        }
        btnLogOut.setOnClickListener{
            //nanti set user ke null
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }
    }
}