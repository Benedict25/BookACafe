package com.example.bookacafe.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.content.res.TypedArray
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookacafe.databinding.MenuBookBinding
import com.example.bookacafe.R
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.ListBookAdapter
import com.google.android.material.tabs.TabLayout

class MenuBook : AppCompatActivity(), View.OnClickListener {
    private var tabLayout: TabLayout? = null
    private lateinit var binding: MenuBookBinding
    private val listBook = ArrayList<Book>()
    private val bookGenre: Array<String> = arrayOf("Romance", "Science", "Fantasy")
    private lateinit var bookTitle: Array<String>
    private lateinit var bookAuthor: Array<String>
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
        showBookDetails()
    }

    private fun getListBooks(selectedTab: Int) : ArrayList<Book> {
        when (selectedTab) {
            0 -> {
                // MASIH MANUAL
                bookTitle = resources.getStringArray(R.array.romance_data_book_author)
                bookAuthor = resources.getStringArray(R.array.romance_data_book_author)
                bookCover = resources.obtainTypedArray(R.array.romance_data_book_cover)
                bookStock = resources.getIntArray(R.array.romance_data_book_stock)
            }
            1 -> {
                // MASIH MANUAL
                bookTitle = resources.getStringArray(R.array.science_data_book_title)
                bookAuthor = resources.getStringArray(R.array.science_data_book_author)
                bookCover = resources.obtainTypedArray(R.array.science_data_book_cover)
                bookStock = resources.getIntArray(R.array.science_data_book_stock)
            }
            2 -> {
                // MASIH MANUAL
                bookTitle = resources.getStringArray(R.array.fantasy_data_book_author)
                bookAuthor = resources.getStringArray(R.array.fantasy_data_book_author)
                bookCover = resources.obtainTypedArray(R.array.fantasy_data_book_cover)
                bookStock = resources.getIntArray(R.array.fantasy_data_book_stock)
            }
        }

        for (position in bookTitle.indices) {
            val book = Book(
                bookTitle[position],
                bookAuthor[position],
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
    }

    private fun showBookDetails() {}
}