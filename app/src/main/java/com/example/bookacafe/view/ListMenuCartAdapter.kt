package com.example.bookacafe.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.databinding.ListCartMenuBinding
import com.example.bookacafe.model.Cart
import com.example.bookacafe.model.Menu
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class ListMenuCartAdapter(private val cart: Cart, val refreshCart: () -> Unit) : RecyclerView.Adapter<ListMenuCartAdapter.ListViewHolder>() {
    var context: Context? = null // Context for Toast to work

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ListCartMenuBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindMenu(cart.menus[position])
        holder.bindQuantity(cart.menuQuantities[position])
    }

    override fun getItemCount(): Int = cart.menus.size

    inner class ListViewHolder(private val binding: ListCartMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMenu(menu: Menu) {
            with(binding) {
                cartFnbName.text = menu.name
                cartFnbPrice.text = "Rp" + DecimalFormat("#,###").format(menu.price)
                Picasso.get().load(menu.imagePath).into(cartFnbPic)
                val control = CartControllers()

                cartSubtractButton.setOnClickListener{
                    val isSubtracted = control.subtractMenuQuantityOnCart(menu.menuId)

                    if (isSubtracted) {
                        Toast.makeText(context, "${menu.name} subtracted", Toast.LENGTH_SHORT).show()
                        refreshCart()
                    } else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }

                cartAddButton.setOnClickListener{
                    val isAdded = control.addMenuQuantityOnCart(menu.menuId)

                    if (isAdded) {
                        Toast.makeText(context, "${menu.name} added", Toast.LENGTH_SHORT).show()
                        refreshCart()
                    } else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        fun bindQuantity(quantity: Int) {
            with(binding) {
                cartFnbQuantity.text = quantity.toString()
            }
        }
    }

}