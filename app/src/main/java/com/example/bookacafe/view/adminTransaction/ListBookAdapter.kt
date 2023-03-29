package com.example.bookacafe.view.adminTransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookacafe.databinding.ItemRowBookBinding
import com.example.bookacafe.model.BookDummy

class ListBookAdapter(private val listBooks: ArrayList<BookDummy>) :  RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBooks.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBooks[position])
    }

    inner class ListViewHolder(private val binding: ItemRowBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookDummy) {
            with(binding){
                Glide.with(itemView)
                    .load(book.bookPict)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgBookPhoto)
                tvItemName.text = book.bookName
                tvItemDescription.text = book.bookDesc
            }
        }
    }
}