package com.example.bookacafe.view.cashierUpdateFnBStatus

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.databinding.ItemRowBookNotServedBinding
import com.example.bookacafe.model.Book

class ListBookAdapter(private val listBook: ArrayList<Book>, private val transactionId: String, private val context: Context, private val onPositiveClickListener: OnPositiveClickListener) :  RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowBookNotServedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBook.size

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pointer = listBook[position]
        holder.itemView.setOnClickListener {
            confirmServedBook(pointer.bookId)
        }
        holder.bind(pointer)
    }

    inner class ListViewHolder(private val binding: ItemRowBookNotServedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            with(binding){
                tvItemName.text = book.title
                tvUserStatus.text = "NOT_SERVED"
            }
        }
    }

    private fun confirmServedBook(bookId: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Serve Book")
        alertDialogBuilder.setMessage("Buku akan di-serve??")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, which -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            dialog, which ->
                val temp = CashierControllers().updateDetailTransactionStatusBook(transactionId, bookId)
                if (temp) {
                    Toast.makeText(context, "Buku sudah di-serve!!", Toast.LENGTH_SHORT).show()
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