package com.example.bookacafe.view.adminTransaction;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookacafe.R;
import com.example.bookacafe.model.adminDummy.MenuDummy

class FoodFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_foods)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(ListFoodAdapter(getListFoods()))

        return view
    }

    fun getListFoods(): ArrayList<MenuDummy> {
        val dataName = resources.getStringArray(R.array.foodName)
        val dataDescription = resources.getStringArray(R.array.foodDesc)
        val dataPict = resources.obtainTypedArray(R.array.foodPict)

        val listFoods = ArrayList<MenuDummy>()
        for (position in dataName.indices) {
            val food = MenuDummy(
                dataPict.getResourceId(position,-1),
                dataName[position],
                dataDescription[position]
            )
            listFoods.add(food)
        }
        return listFoods
    }
}