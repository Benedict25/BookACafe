package com.example.bookacafe.view.adminDisabledUser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.AdminDisabledUserBinding
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.model.adminDataDetails.MemberDummy
import com.example.bookacafe.view.cashierUpdateFnBStatus.CashierUpdateNotServedStatus

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
        finish()
        overridePendingTransition(0, 0)
        val intent = Intent(this, DisabledUserMenu::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}