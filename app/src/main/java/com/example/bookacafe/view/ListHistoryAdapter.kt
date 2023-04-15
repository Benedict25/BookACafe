package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.R
import com.example.bookacafe.databinding.ItemRowHistoryBinding
import com.example.bookacafe.model.Transaction
import com.example.bookacafe.model.TransactionEnum

class ListHistoryAdapter(private val listHistory: ArrayList<Transaction>) :
    RecyclerView.Adapter<ListHistoryAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            ItemRowHistoryBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    inner class ViewHolder(binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvHistoryInvoiceNumber = binding.tvHistoryInvoiceNumber
        val tvHistoryInvoiceDate = binding.tvHistoryInvoiceDate
        val layout = binding.layoutHistory
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = listHistory[position]
        holder.tvHistoryInvoiceNumber.text = history.transactionId
        holder.tvHistoryInvoiceDate.text = history.checkedIn.toString()

        if (history.status == TransactionEnum.PENDING) {
            holder.layout.setBackgroundResource(R.drawable.history_background_pending)
        } else if (history.status == TransactionEnum.PAID || history.status == TransactionEnum.CANCELLED) {
            holder.layout.setBackgroundResource(R.drawable.history_background_paid)
        } else if (history.status == TransactionEnum.NOT_PAID) {
            holder.layout.setBackgroundResource(R.drawable.history_background_on_going)
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, history)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Transaction)
    }

    companion object {
        fun setOnClickListener(
            listHistoryAdapter: ListHistoryAdapter,
            onClickListener: OnClickListener
        ) {
            listHistoryAdapter.onClickListener = onClickListener
        }
    }
}