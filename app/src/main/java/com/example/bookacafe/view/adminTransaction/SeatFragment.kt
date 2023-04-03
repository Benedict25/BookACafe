package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.Fragment


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookacafe.R;
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.model.adminDataDetails.TableDummy

class SeatFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_seat, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_seats)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(ListSeatAdapter(AdminControllers().getSeatData()))

        return view
    }
}