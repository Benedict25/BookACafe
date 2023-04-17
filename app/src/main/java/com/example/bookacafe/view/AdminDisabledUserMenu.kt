package com.example.bookacafe.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.AdminDisabledUserBinding
import com.example.bookacafe.model.AdminMemberDetails
import java.util.*
import kotlin.collections.ArrayList

class AdminDisabledUserMenu: AppCompatActivity(), AdminListUserAdapter.OnPositiveClickListener {
    private lateinit var binding: AdminDisabledUserBinding
    private var list = ArrayList<AdminMemberDetails>()

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
        binding.rvUsers.layoutManager = LinearLayoutManager(this@AdminDisabledUserMenu)
        val listUserAdapter = AdminListUserAdapter(list,this, this)
        binding.rvUsers.adapter = listUserAdapter
    }

    override fun onPositiveClick() {
        finish()
        overridePendingTransition(0, 0)
        val intent = Intent(this, AdminDisabledUserMenu::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}