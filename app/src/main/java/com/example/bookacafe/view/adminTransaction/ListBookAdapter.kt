package com.example.bookacafe.view.adminTransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookacafe.databinding.ItemRowBookBinding
import com.example.bookacafe.model.adminDataDetails.AdminBookDetails

class ListBookAdapter(private val listBooks: ArrayList<AdminBookDetails>) :  RecyclerView.Adapter<ListBookAdapter.ListViewHolderBook>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolderBook {
        val binding = ItemRowBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolderBook(binding)
    }

    override fun getItemCount(): Int = listBooks.size

    override fun onBindViewHolder(holder: ListViewHolderBook, position: Int) {
        holder.bind(listBooks[position])
    }

    inner class ListViewHolderBook(private val binding: ItemRowBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: AdminBookDetails) {
            with(binding){
                Glide.with(itemView)
                    .load(book.bookPict)
                    .into(imgBookPhoto)
                tvItemName.text = book.bookName
                tvItemDescription.text = book.bookDesc
            }
        }
    }

}
