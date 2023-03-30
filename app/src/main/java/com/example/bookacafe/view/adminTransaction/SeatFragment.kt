package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.Fragment


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookacafe.R;
import com.example.bookacafe.model.adminDummy.TableDummy

class SeatFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_seat, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_seats)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(ListSeatAdapter(getListSeats()))

        return view
    }

    fun getListSeats(): ArrayList<TableDummy> {
        val dataName = resources.getStringArray(R.array.seatNum)
        val dataDescription = resources.getStringArray(R.array.seatDesc)

        val listSeats = ArrayList<TableDummy>()
        for (position in dataName.indices) {
            val seat = TableDummy(
                dataName[position],
                dataDescription[position]
            )
            listSeats.add(seat)
        }
        return listSeats
    }

}