package com.example.bookacafe.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.BookControllers
import com.example.bookacafe.databinding.ItemBookBinding
import com.example.bookacafe.model.Book
import com.squareup.picasso.Picasso

class ListBookAdapter(private val books : ArrayList<Book>) : RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {
    var onItemClick: ((Book) -> Unit)? = null
    var context: Context? = null

    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(books[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(books[position])
        }
    }

    inner class ListViewHolder(private val binding : ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book : Book) {
            with(binding) {
                tvBookTitle.text = book.title
                tvBookAuthor.text = book.author
                tvBookStock.text = "Stock: ${book.stock}"
                Picasso.get().load(book.imagePath).into(imgBookCover)
                btnAdd.setOnClickListener() {
                    showAddToCartDialog(book)
                }
            }
        }
    }

    private fun showAddToCartDialog(book: Book) {
        val addToCartDialog = AlertDialog.Builder(context)

        val positiveButtonClick = { _: DialogInterface, _: Int ->
            Toast.makeText(context,
                book.title + " added to cart.",
                Toast.LENGTH_SHORT
            ).show()
            BookControllers().addBookToCart(book.bookId)
            addToCartDialog.create().dismiss()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->

        }


        addToCartDialog.setTitle("Add to Cart")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("You chose " + book.title)
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }
}