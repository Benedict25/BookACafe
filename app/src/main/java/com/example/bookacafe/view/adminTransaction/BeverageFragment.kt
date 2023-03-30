package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookacafe.R;
import com.example.bookacafe.model.MenuDummy

class BeverageFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beverage, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_beverages)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(ListBeverageAdapter(getListBeverages()))

        return view
    }

    fun getListBeverages(): ArrayList<MenuDummy> {
        val dataName = resources.getStringArray(R.array.beverageName)
        val dataDescription = resources.getStringArray(R.array.beverageDesc)
        val dataPict = resources.obtainTypedArray(R.array.beveragePict)

        val listBeverages = ArrayList<MenuDummy>()
        for (position in dataName.indices) {
            val beverage = MenuDummy(
                dataPict.getResourceId(position,-1),
                dataName[position],
                dataDescription[position]
            )
            listBeverages.add(beverage)
        }
        return listBeverages
    }
}