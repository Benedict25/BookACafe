package com.example.bookacafe.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.databinding.ListCartBookBinding
import com.example.bookacafe.databinding.ListCartMenuBinding
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.Cart
import com.example.bookacafe.model.Menu
import com.squareup.picasso.Picasso

class ListBookCartAdapter(private val cart: Cart) : RecyclerView.Adapter<ListBookCartAdapter.ListViewHolder>() {
    var context: Context? = null // Context for Toast to work

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ListCartBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindBook(cart.books[position])
    }

    override fun getItemCount(): Int = cart.books.size

    inner class ListViewHolder(private val binding: ListCartBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindBook(book: Book) {
            with(binding) {
                cartBookName.text = book.title
                Picasso.get().load(book.imagePath).into(cartBookPic)
                val control: CartControllers = CartControllers()

                cartBookCancel.setOnClickListener {
                    val isRemoved = control.RemoveBookFromCart(book.bookId)

                    if (isRemoved) {
                        Toast.makeText(context, "${book.title} Removed", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MenuCart::class.java)
                        context?.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}