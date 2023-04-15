package com.example.bookacafe.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.TransactionControllers
import com.example.bookacafe.databinding.HistoryScreenBinding
import com.example.bookacafe.model.Transaction
import com.example.bookacafe.view.ListHistoryAdapter.OnClickListener

class HistoryActivity : AppCompatActivity() {
    var binding: HistoryScreenBinding? = null
    private val list = ArrayList<Transaction>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HistoryScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        list.addAll(getListHistory())

        binding?.rvHistory?.setHasFixedSize(true)
        showRecyclerListHistory()
    }

    private fun showRecyclerListHistory() {
        binding?.rvHistory?.layoutManager = LinearLayoutManager(this)
        val listHistoryAdapter = ListHistoryAdapter(list)
        binding?.rvHistory?.adapter = listHistoryAdapter

        ListHistoryAdapter.setOnClickListener(
            listHistoryAdapter,
            object : OnClickListener {
                override fun onClick(position: Int, model: Transaction) {
                    moveToBillPage(model)
                }
            })
    }

    fun moveToBillPage(model: Transaction) {
        val intent = Intent(this@HistoryActivity, BillActivity::class.java)
        intent.putExtra("transaction_id", model.transactionId)
        startActivity(intent)
    }

    private fun getListHistory(): ArrayList<Transaction> {
        return TransactionControllers.getTransactionData()
    }
}