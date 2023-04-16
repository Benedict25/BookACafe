package com.example.bookacafe.view.cashierUpdateFnBStatus

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.databinding.ItemRowBookNotServedBinding
import com.example.bookacafe.model.adminDataDetails.CashierBookDetail

class ListBookAdapter(private val listBook: ArrayList<CashierBookDetail>, private val transactionId: String, private val context: Context, private val onPositiveClickListener: OnPositiveClickListener) :  RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowBookNotServedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBook.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pointer = listBook[position]
        holder.itemView.setOnClickListener {
            confirmServedBook(pointer.detailTransactionId)
        }
        holder.bind(pointer)
    }

    inner class ListViewHolder(private val binding: ItemRowBookNotServedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: CashierBookDetail) {
            with(binding){
                tvItemName.text = book.bookTitle
                tvUserStatus.text = "NOT_SERVED"
            }
        }
    }

    private fun confirmServedBook(detailTransactionId: Int) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Serve Book")
        alertDialogBuilder.setMessage("Serve the book?")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, which -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            dialog, which ->
                val temp = CashierControllers().updateDetailTransactionStatus(detailTransactionId)
                if (temp) {
                    Toast.makeText(context, "Book has been served!", Toast.LENGTH_SHORT).show()
                }
                onPositiveClickListener.onPositiveClick()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    interface OnPositiveClickListener {
        fun onPositiveClick()
    }
}