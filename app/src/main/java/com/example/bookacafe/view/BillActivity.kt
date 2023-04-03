package com.example.bookacafe.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.databinding.BillScreenBinding
import com.example.bookacafe.model.Menu

class BillActivity : AppCompatActivity() {
    private lateinit var binding: BillScreenBinding
    private val list = ArrayList<Menu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BillScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBillMenu.setHasFixedSize(true)

        list.addAll(getListMenu())
        showRecyclerList()
    }

    fun getListMenu(): ArrayList<Menu> {
        val name = resources.getStringArray(R.array.foodName)
        val price = resources.getStringArray(R.array.foodPrice)
        val listMenu = ArrayList<Menu>()

        for (position in name.indices) {
            val menu = Menu(
                "M000",
                name[position],
                100000,
                0
            )

            listMenu.add(menu)
        }

        return listMenu
    }

    private fun showRecyclerList() {
        binding.rvBillMenu.layoutManager = LinearLayoutManager(this)
        val listMenuAdapter = BillFnBAdapter(list)
        binding.rvBillMenu.adapter = listMenuAdapter
    }
}