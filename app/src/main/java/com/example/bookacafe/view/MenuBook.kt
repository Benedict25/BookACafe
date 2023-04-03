package com.example.bookacafe.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.BookControllers
import com.example.bookacafe.databinding.MenuBookBinding
import com.example.bookacafe.model.Book
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class MenuBook : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MenuBookBinding
    private lateinit var dialog: Dialog
    private var tabLayout: TabLayout? = null
    private var books = ArrayList<Book>()
    private var selectedTab: Int = 0
    private val bookGenre: Array<String> = arrayOf("Romance", "Science", "Fantasy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tab Layout & View Pager (Genre)
        tabLayout = findViewById(R.id.tl_books)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(bookGenre[0]))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(bookGenre[1]))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(bookGenre[2]))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                books.clear()
                selectedTab = tab.position
                getListBooks(selectedTab)
                showBooks()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        // Show Books
        getListBooks(selectedTab)
        showBooks()
    }

    override fun onClick(p0: View?) {

    }

    private fun getListBooks(selectedTab: Int) : ArrayList<Book> {
        when (selectedTab) {
            0 -> {
                books = BookControllers().getBookData(bookGenre[0].lowercase())
            }
            1 -> {
                books = BookControllers().getBookData(bookGenre[1].lowercase())
            }
            2 -> {
                books = BookControllers().getBookData(bookGenre[2].lowercase())
            }
        }

        return books
    }

    private fun showBooks() {
        binding.rvBooks.layoutManager = GridLayoutManager(this, 2)
        val listBookAdapter = ListBookAdapter(books)
        binding.rvBooks.adapter = listBookAdapter

        listBookAdapter.onItemClick = {
            book -> showBookDetails(book)
        }
    }

    private fun showBookDetails(book: Book) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.book_details_dialog)
        dialog.setTitle("Book Detail")

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener() {
            dialog.dismiss()
        }

        val bookCover = dialog.findViewById(R.id.img_book_cover) as ImageView
        Picasso.get().load(book.imagePath).into(bookCover)

        val bookTitle = dialog.findViewById(R.id.tv_book_title) as TextView
        bookTitle.text = book.title
        val bookAuthor = dialog.findViewById(R.id.tv_book_author) as TextView
        bookAuthor.text = book.author
        val bookStock = dialog.findViewById(R.id.tv_book_stock) as TextView
        bookStock.text = "Stock: ${book.stock}"
        val bookSynopsis = dialog.findViewById(R.id.tv_book_synopsis) as TextView
        bookSynopsis.text = book.synopsis

        val btnAddToCart = dialog.findViewById(R.id.btn_add_to_cart) as Button
        btnAddToCart.setOnClickListener() {
            showAddToCartDialog(book)
        }

        dialog.show()
    }

    private fun showAddToCartDialog(book: Book) {
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this@MenuBook,
                book.title + " added to cart.",
                Toast.LENGTH_SHORT
            ).show()
            TODO("Book Controllers: Add to Cart")
            dialog.dismiss()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val addToCartDialog = AlertDialog.Builder(this@MenuBook)
        addToCartDialog.setTitle("Add to Cart")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("You chose " + book.title)
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }
}