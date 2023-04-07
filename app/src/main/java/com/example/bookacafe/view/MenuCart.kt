package com.example.bookacafe.view

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.databinding.ActivityMenuCartBinding
import com.example.bookacafe.databinding.ListCartBookBinding
import com.example.bookacafe.databinding.ListCartMenuBinding
import com.example.bookacafe.databinding.MenuBookBinding
import com.example.bookacafe.model.Cart

class MenuCart : AppCompatActivity() {

    private lateinit var binding: ActivityMenuCartBinding
    private lateinit var cart: Cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val control: CartControllers = CartControllers()
        cart = control.GetCartData()

        setSeat(cart)
        showCartMenus()
        showCartBooks()
        setTotal(control.getTotalForCart())
    }

    fun setSeat(cart: Cart) {
        var cartRoomNumber: Button = findViewById(R.id.cartRoomNumber)
        var cartTableNumber: TextView = findViewById(R.id.cartTableNumber)

        cartRoomNumber.text = cart.table.room
        cartTableNumber.text = "Table " + cart.table.tableName
    }

    fun showCartMenus() {
        binding.rvCartMenu.layoutManager = LinearLayoutManager(this)
        val listMenuCartAdapter = ListMenuCartAdapter(cart)
        binding.rvCartMenu.adapter = listMenuCartAdapter
    }

    fun showCartBooks() {
        binding.rvCartBook.layoutManager = LinearLayoutManager(this)
        val listBookCartAdapter = ListBookCartAdapter(cart)
        binding.rvCartBook.adapter = listBookCartAdapter
    }

    fun setTotal(total: Int) {
        var cartTotal: TextView = findViewById(R.id.cartTotal)
        cartTotal.text = "Rp$total,-"
    }
}