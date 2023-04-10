package com.example.bookacafe.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.controller.OrderControllers
import com.example.bookacafe.databinding.*
import com.example.bookacafe.model.Cart


class MenuCart : Fragment(), View.OnClickListener {

    private lateinit var binding: MenuCartBinding
    private lateinit var cart: Cart

    // Button
    lateinit var cartTableCancel: ImageView
    lateinit var cartOrder: Button
    lateinit var cartPay: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(p0: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val control: CartControllers = CartControllers()
        cart = control.GetCartData()

        setTable(cart, view)
        showCartMenus()
        showCartBooks()
        setTotal(control.getTotalForCart(), view)

        cartTableCancel = view.findViewById(R.id.cartTableCancel)
        cartTableCancel.setOnClickListener {
            control.RemoveTableFromCart()
            Toast.makeText(requireContext(), "Table ${cart.table.tableName} Removed", Toast.LENGTH_SHORT).show()
            refreshCart()
        }

        cartOrder = view.findViewById(R.id.cartOrder)
        cartOrder.setOnClickListener {
            val control: OrderControllers = OrderControllers()
            val isOrdered = control.order()

            if (isOrdered) {
                Toast.makeText(requireContext(), "Order Success!", Toast.LENGTH_SHORT).show()
                refreshCart()
            } else {
                Toast.makeText(requireContext(), "Order Error!", Toast.LENGTH_SHORT).show()
            }
        }

        cartPay = view.findViewById(R.id.cartPay)
        cartPay.setOnClickListener {

        }
    }

    fun setTable(cart: Cart, view: View) {
        var cartRoomNumber: Button = view.findViewById(R.id.cartRoomNumber)
        var cartTableNumber: TextView = view.findViewById(R.id.cartTableNumber)

        cartRoomNumber.text = cart.table.room
        cartTableNumber.text = "Table " + cart.table.tableName
    }

    fun showCartMenus() {
        binding.rvCartMenu.layoutManager = LinearLayoutManager(requireContext())
        val listMenuCartAdapter = ListMenuCartAdapter(cart) { refreshCart() }
        binding.rvCartMenu.adapter = listMenuCartAdapter
    }

    fun showCartBooks() {
        binding.rvCartBook.layoutManager = LinearLayoutManager(requireContext())
        val listBookCartAdapter = ListBookCartAdapter(cart) { refreshCart() }
        binding.rvCartBook.adapter = listBookCartAdapter
    }

    fun setTotal(total: Int, view: View) {
        var cartTotal: TextView = view.findViewById(R.id.cartTotal)
        cartTotal.text = "Rp$total,-"
    }

    fun refreshCart(){
        // Refresh For Fragment
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()

        // Refresh For Activity
//        finish()
//        overridePendingTransition(0, 0) // OverridePending biar pas refresh gada animasi blink
//        startActivity(intent)
//        overridePendingTransition(0, 0)
    }
}