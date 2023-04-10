package com.example.bookacafe.view.adminDisabledUser

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.AdminDisabledUserBinding
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.model.adminDataDetails.MemberDummy
import com.example.bookacafe.view.cashierUpdateFnBStatus.CashierUpdateNotServedStatus
import java.util.*
import kotlin.collections.ArrayList

class DisabledUserMenu: AppCompatActivity(), ListUserAdapter.OnPositiveClickListener {
    private lateinit var binding: AdminDisabledUserBinding
    private var list = ArrayList<MemberDummy>()

    private val mHandler = Handler()
    private lateinit var mTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminDisabledUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTimer = Timer()
        mTimer.scheduleAtFixedRate(RefreshTask(), 0, 10000)

        binding.rvUsers.setHasFixedSize(true)

        list.addAll(AdminControllers().getUserDetails())
        showRecyclerList()
    }

    inner class RefreshTask : TimerTask() {
        override fun run() {
            // Post ke Handler untuk memperbarui UI di Thread UI
            mHandler.post {
                list.clear()
                list.addAll(AdminControllers().getUserDetails())
                showRecyclerList()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer.cancel()
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