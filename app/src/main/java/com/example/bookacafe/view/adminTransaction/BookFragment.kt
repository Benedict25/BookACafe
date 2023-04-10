package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookacafe.R;
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.AdminTransactionBinding
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail

class BookFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var binding: AdminTransactionBinding
    private val list = ArrayList<CashierMenuDetail>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_book, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_books)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(ListBookAdapter(AdminControllers().getBookData()))

        return view
    }
}