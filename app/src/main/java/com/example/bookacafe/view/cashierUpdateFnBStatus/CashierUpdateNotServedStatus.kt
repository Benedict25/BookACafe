package com.example.bookacafe.view.cashierUpdateFnBStatus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.databinding.CashierFnbNotServedBinding
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.adminDataDetails.CashierBookDetail
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.view.CashierActivity
import kotlin.collections.ArrayList

class CashierUpdateNotServedStatus: AppCompatActivity(), ListFnBAdapter.OnPositiveClickListener, ListBookAdapter.OnPositiveClickListener {
    private lateinit var binding: CashierFnbNotServedBinding
    private val listMenu = ArrayList<CashierMenuDetail>()
    private val listBook = ArrayList<CashierBookDetail>()
    private lateinit var transactionId:String
    private lateinit var tableName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CashierFnbNotServedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tableNameTemp = intent.getStringExtra("table_key")
        val transactionIdTemp = intent.getStringExtra("trans_id")

        binding.rvUsers.setHasFixedSize(true)
        if (tableNameTemp != null && transactionIdTemp != null) {
            transactionId = transactionIdTemp
            tableName = tableNameTemp
            listMenu.addAll(CashierControllers().getOrderedMenuData(tableName, true))
            listBook.addAll(CashierControllers().getOrderedBookData(tableName, true))
            showRecyclerList()
        }

    }

    private fun showRecyclerList() {

        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        val listBookAdapter = ListBookAdapter(listBook,transactionId, this, this)
        binding.rvBooks.adapter = listBookAdapter

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listFnBAdapter = ListFnBAdapter(listMenu,transactionId, this, this)
        binding.rvUsers.adapter = listFnBAdapter


    }

    override fun onPositiveClick() {
        if (CashierControllers().getNotServedMenu(transactionId)) {
            finish()
            overridePendingTransition(0, 0)
            val intent = Intent(this, CashierUpdateNotServedStatus::class.java)
            intent.putExtra("table_key", tableName)
            intent.putExtra("trans_id", transactionId)
            startActivity(intent)
            overridePendingTransition(0, 0)
        } else {
            finish()
            val intent = Intent(this, CashierActivity::class.java)
            startActivity(intent)
        }

    }
}