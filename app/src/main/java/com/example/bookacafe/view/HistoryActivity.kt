package com.example.bookacafe.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.MemberControllers
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
                    Toast.makeText(
                        applicationContext,
                        "Ini toast, trans id: ${model.transactionId}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun getListHistory(): ArrayList<Transaction> {
        var listHistory = MemberControllers.ShowHistory()
        return listHistory
    }
}