package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookacafe.R
import com.example.bookacafe.databinding.ItemBillBookRowBinding
import com.example.bookacafe.model.Book

class BillBookAdapter(private val listBook: ArrayList<Book>) :
    RecyclerView.Adapter<BillBookAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: ItemBillBookRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(book: Book){
                with(binding) {
                    bookData.text = book.title
                }
            }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemBillBookRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBook.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBook[position])
    }

}