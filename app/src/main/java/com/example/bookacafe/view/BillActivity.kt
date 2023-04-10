package com.example.bookacafe.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.MemberControllers
import com.example.bookacafe.controller.TransactionControllers
import com.example.bookacafe.databinding.BillScreenBinding
import com.example.bookacafe.model.*

class BillActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TRANSACTION = "extra_transaction"
    }

    var transaction = intent.getParcelableExtra<Transaction>(EXTRA_TRANSACTION)
    private lateinit var binding: BillScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null){

        }
        binding = BillScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBillMenu.setHasFixedSize(true)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvBillMenu.layoutManager = LinearLayoutManager(this)
        val listMenuAdapter = transaction?.let { BillFnBAdapter(it.menus) }
        binding.rvBillMenu.adapter = listMenuAdapter
    }
}