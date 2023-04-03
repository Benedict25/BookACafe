package com.example.bookacafe.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.MemberControllers
import com.example.bookacafe.databinding.HistoryScreenBinding
import com.example.bookacafe.model.Transaction

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: HistoryScreenBinding
    private val list = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HistoryScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.rvHistory.setHasFixedSize(true)

        list.addAll(getListHistory())
        showRecyclerListHistory()
    }

    private fun showRecyclerListHistory() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        val listHistoryAdapter = ListHistoryAdapter(list)
        binding.rvHistory.adapter = listHistoryAdapter
    }

    fun getListHistory(): ArrayList<Transaction> {
//        val invoiceNumber = resources.getStringArray(R.array.invoice_number)
//        val invoiceDate = resources.getStringArray(R.array.invoice_date)
//
//        val listHistory = ArrayList<Transaction>()
//        for (position in invoiceNumber.indices) {
//            val history = Transaction(
//                transactionId = invoiceNumber[position],
//                checkedIn = invoiceDate[position]
//            )
//            listHistory.add(history)
//        }

        var listHistory = MemberControllers.ShowHistory()
        return listHistory
    }
}