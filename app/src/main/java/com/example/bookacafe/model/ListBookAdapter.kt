package com.example.bookacafe.model

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemBookBinding

class ListBookAdapter(private val listBook : ArrayList<Book>) : RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBook.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBook[position])
    }

    inner class ListViewHolder(private val binding : ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book : Book) {
            with(binding) {
                tvBookTitle.text = book.title
                tvBookAuthor.text = book.author
                tvBookStock.text = "Stock: ${book.stock}"
                Log.d("IMAGE PATH: ", book.imagePath.toString())
                imgBookCover.setImageResource(book.imagePath)
            }
        }
    }
}