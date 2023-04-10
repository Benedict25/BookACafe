package com.example.bookacafe.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.BookControllers
import com.example.bookacafe.model.Book
import com.squareup.picasso.Picasso
import android.widget.*
import com.example.bookacafe.databinding.MenuBookBinding
import com.google.android.material.tabs.TabLayout


class BookMenu : Fragment(), View.OnClickListener {
    private lateinit var binding: MenuBookBinding
    private lateinit var dialog: Dialog
    private lateinit var searchView: SearchView
    private var tabLayout: TabLayout? = null
    private var books = ArrayList<Book>()
    private var bookTitles = ArrayList<String>()
    private var selectedTab: Int = 0
    private val bookGenre: Array<String> = arrayOf("Romance", "Science", "Fantasy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show Books
        getListBooks(selectedTab, "")
        showBooks()

        // Search View (per Genre)
        searchView = view.findViewById(R.id.search_view)
        for (book in books) {
            bookTitles.add(book.title)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (bookTitles.contains(query)) {
                    getListBooks(selectedTab, query.toString())
                    showBooks()
                } else {
                    Toast.makeText(requireContext(),
                        "No book found.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getListBooks(selectedTab, newText.toString())
                showBooks()
                return false
            }
        })

        // Tab Layout & View Pager (Genre)
        tabLayout = view.findViewById(R.id.tl_books)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(bookGenre[0]))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(bookGenre[1]))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(bookGenre[2]))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                books.clear()
                selectedTab = tab.position
                searchView.setQuery("", false)
                var queryHint = ""
                when (selectedTab) {
                    0 -> {
                        queryHint = "Romance book sounds great!"
                    }
                    1 -> {
                        queryHint = "Learning never exhausts the mind."
                    }
                    2 -> {
                        queryHint = "I recommend you Bumi by Tere Liye."
                    }
                }
                searchView.queryHint = queryHint

                getListBooks(selectedTab, "")
                showBooks()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
    override fun onClick(p0: View?) {

    }

    private fun getListBooks(selectedTab: Int, inputText: String) : ArrayList<Book> {
        when (selectedTab) {
            0 -> {
                books = BookControllers().getBookData(bookGenre[0].lowercase(), inputText)
            }
            1 -> {
                books = BookControllers().getBookData(bookGenre[1].lowercase(), inputText)
            }
            2 -> {
                books = BookControllers().getBookData(bookGenre[2].lowercase(), inputText)
            }
        }

        return books
    }

    private fun showBooks() {
        binding.rvBooks.layoutManager = GridLayoutManager(requireContext(), 2)
        val listBookAdapter = ListBookAdapter(books)
        binding.rvBooks.adapter = listBookAdapter

        listBookAdapter.onItemClick = {
                book -> showBookDetails(book)
        }
    }

    private fun showBookDetails(book: Book) {
        dialog = Dialog(requireContext())
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
            val added = BookControllers().addBookToCart(book.bookId)
            val text: String
            if (added) {
                text = book.title + " added to cart."
            } else {
                text = book.title + " is already in your cart!"
            }
            val toast = Toast.makeText(requireContext(),
                text,
                Toast.LENGTH_SHORT
            )
            val layout = toast.view as LinearLayout?
            if (layout!!.childCount > 0) {
                val tv = layout!!.getChildAt(0) as TextView
                tv.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            }
            toast.show()
            dialog.dismiss()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val addToCartDialog = AlertDialog.Builder(requireContext())
        addToCartDialog.setTitle("Add to Cart")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("You chose " + book.title)
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }
}