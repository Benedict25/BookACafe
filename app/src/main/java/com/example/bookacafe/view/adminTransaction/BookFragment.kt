package com.example.bookacafe.view.adminTransaction;

import android.annotation.SuppressLint
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookacafe.R;
import com.example.bookacafe.databinding.AdminDisabledUserBinding
import com.example.bookacafe.databinding.AdminTransactionBinding
import com.example.bookacafe.model.BookDummy
import com.example.bookacafe.model.MemberDummy

class BookFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var binding: AdminTransactionBinding
    private val list = ArrayList<MemberDummy>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_book, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_books)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(ListBookAdapter(getListBooks()))

        return view
    }

    fun getListBooks(): ArrayList<BookDummy> {
        val dataName = resources.getStringArray(R.array.bookName)
        val dataDescription = resources.getStringArray(R.array.bookDesc)
        val dataPict = resources.obtainTypedArray(R.array.bookPict)

        val listBooks = ArrayList<BookDummy>()
        for (position in dataName.indices) {
            val book = BookDummy(
                dataPict.getResourceId(position,-1),
                dataName[position],
                dataDescription[position]
            )
            listBooks.add(book)
        }
        return listBooks
    }
}