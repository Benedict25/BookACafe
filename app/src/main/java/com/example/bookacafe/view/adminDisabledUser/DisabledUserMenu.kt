package com.example.bookacafe.view.adminDisabledUser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.AdminDisabledUserBinding
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.model.adminDataDetails.MemberDummy

class DisabledUserMenu: AppCompatActivity(), ListUserAdapter.OnPositiveClickListener {
    private lateinit var binding: AdminDisabledUserBinding
    private val list = ArrayList<MemberDummy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminDisabledUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        list.addAll(AdminControllers().getUserDetails())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this@DisabledUserMenu)
        val listUserAdapter = ListUserAdapter(list,this, this)
        binding.rvUsers.adapter = listUserAdapter
    }

    override fun onPositiveClick() {
        val intent = Intent(this, DisabledUserMenu::class.java)
        startActivity(intent)
        finish()
    }
}