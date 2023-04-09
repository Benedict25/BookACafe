package com.example.bookacafe.view.cashierTransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemBillBookRowBinding
import com.example.bookacafe.model.Book

class CashierBookAdapter(private val listBook: ArrayList<Book>): RecyclerView.Adapter<CashierBookAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemBillBookRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book){
            with(binding){
                bookData.text = book.title
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemBillBookRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBook[position])
    }
}