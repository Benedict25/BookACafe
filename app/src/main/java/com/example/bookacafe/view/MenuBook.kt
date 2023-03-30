package com.example.bookacafe.view

import android.app.Dialog
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.databinding.MenuBookBinding
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.ListBookAdapter
import com.google.android.material.tabs.TabLayout


class MenuBook : AppCompatActivity(), View.OnClickListener {
    private var tabLayout: TabLayout? = null
    private lateinit var binding: MenuBookBinding
    private val listBook = ArrayList<Book>()
    private val bookGenre: Array<String> = arrayOf("Romance", "Science", "Fantasy")
    private lateinit var bookId: Array<String>
    private lateinit var bookTitle: Array<String>
    private lateinit var bookAuthor: Array<String>
    private lateinit var bookSynopsis: Array<String>
    private lateinit var bookCover: TypedArray
    private lateinit var bookStock: IntArray
    private var selectedTab: Int = 0

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
                listBook.clear()
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
                // MASIH MANUAL
                bookId = resources.getStringArray(R.array.romance_data_book_id)
                bookTitle = resources.getStringArray(R.array.romance_data_book_title)
                bookAuthor = resources.getStringArray(R.array.romance_data_book_author)
                bookSynopsis = resources.getStringArray(R.array.romance_data_book_synopsis)
                bookCover = resources.obtainTypedArray(R.array.romance_data_book_cover)
                bookStock = resources.getIntArray(R.array.romance_data_book_stock)
            }
            1 -> {
                // MASIH MANUAL
                bookId = resources.getStringArray(R.array.science_data_book_id)
                bookTitle = resources.getStringArray(R.array.science_data_book_title)
                bookAuthor = resources.getStringArray(R.array.science_data_book_author)
                bookSynopsis = resources.getStringArray(R.array.science_data_book_synopsis)
                bookCover = resources.obtainTypedArray(R.array.science_data_book_cover)
                bookStock = resources.getIntArray(R.array.science_data_book_stock)
            }
            2 -> {
                // MASIH MANUAL
                bookId = resources.getStringArray(R.array.fantasy_data_book_id)
                bookTitle = resources.getStringArray(R.array.fantasy_data_book_title)
                bookAuthor = resources.getStringArray(R.array.fantasy_data_book_author)
                bookSynopsis = resources.getStringArray(R.array.fantasy_data_book_synopsis)
                bookCover = resources.obtainTypedArray(R.array.fantasy_data_book_cover)
                bookStock = resources.getIntArray(R.array.fantasy_data_book_stock)
            }
        }

        for (position in bookTitle.indices) {
            val book = Book(
                bookId[position],
                bookTitle[position],
                bookAuthor[position],
                bookSynopsis[position],
                bookCover.getResourceId(position, -1),
                bookStock[position]
            )
            listBook.add(book)
        }
        return listBook
    }

    private fun showBooks() {
        binding.rvBooks.layoutManager = GridLayoutManager(this, 2)
        val listBookAdapter = ListBookAdapter(listBook)
        binding.rvBooks.adapter = listBookAdapter

        listBookAdapter.onItemClick = {
            book -> showBookDetails(book)
        }
    }

    private fun showBookDetails(book: Book) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.book_details_dialog)
        dialog.setTitle("Book Detail")

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener() {
            dialog.dismiss()
        }

        val bookCover = dialog.findViewById(R.id.img_book_cover) as ImageView
        bookCover.setImageResource(book.imagePath)
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
            TODO("Add to Cart")
        }

        dialog.show()
    }
}