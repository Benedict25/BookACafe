package com.example.bookacafe.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemRowHistoryBinding
import com.example.bookacafe.model.Transaction

class ListHistoryAdapter(private val listHistory: ArrayList<Transaction>) :
    RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(history: Transaction) {
            with(binding) {
                tvHistoryInvoiceNumber.text = "Invoice Number: " + history.transactionId
                tvHistoryInvoiceDate.text = history.checkedIn
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }
}