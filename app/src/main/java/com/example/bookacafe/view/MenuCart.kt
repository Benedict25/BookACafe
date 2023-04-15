package com.example.bookacafe.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.controller.OrderControllers
import com.example.bookacafe.databinding.*
import com.example.bookacafe.model.Cart
import java.text.DecimalFormat

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

        val control = CartControllers()
        cart = control.getCartData()

        setTable(cart, view)
        showCartMenus()
        showCartBooks()
        setTotal(control.getTotalForCart(), view)

        cartTableCancel = view.findViewById(R.id.cartTableCancel)
        cartTableCancel.setOnClickListener {
            control.removeTableFromCart()
            Toast.makeText(requireContext(), "Table ${cart.table.tableName} removed", Toast.LENGTH_SHORT).show()
            refreshCart()
        }

        cartOrder = view.findViewById(R.id.cartOrder)
        cartOrder.setOnClickListener {
            showOrderConfirmationDialog()
            refreshCart()
        }

        cartPay = view.findViewById(R.id.cartPay)
        cartPay.setOnClickListener {
            val intent = Intent(context, BillActivity::class.java)
            intent.putExtra("transaction_id", "TR20230211-001")
            startActivity(intent)
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
        var totalFormat = DecimalFormat("#,###").format(total)
        cartTotal.text = "Rp$totalFormat"
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

    private fun showOrderConfirmationDialog() {
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            val control = OrderControllers()
            val isOrdered = control.order()

            if (isOrdered) {
                Toast.makeText(requireContext(), "Order success!", Toast.LENGTH_SHORT).show()
                refreshCart()
            } else {
                Toast.makeText(requireContext(), "Order error!", Toast.LENGTH_SHORT).show()
            }
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->

        }

        val addToCartDialog = AlertDialog.Builder(requireContext())
        addToCartDialog.setTitle("Order")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("Are you sure you want to order?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }
}