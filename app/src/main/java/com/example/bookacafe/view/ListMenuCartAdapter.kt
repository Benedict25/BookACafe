package com.example.bookacafe.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.databinding.ListCartMenuBinding
import com.example.bookacafe.model.Cart
import com.example.bookacafe.model.Menu
import com.squareup.picasso.Picasso

class ListMenuCartAdapter(private val cart: Cart) : RecyclerView.Adapter<ListMenuCartAdapter.ListViewHolder>() {
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
                cartFnbPrice.text = "Rp" + menu.price.toString() + ",-"
                Picasso.get().load(menu.imagePath).into(cartFnbPic)
                val control: CartControllers = CartControllers()

                cartSubstractButton.setOnClickListener{
                    val isSubstracted = control.SubstractMenuQuantityOnCart(menu.menuId)

                    if (isSubstracted) {
                        Toast.makeText(context, "${menu.name} Substracted", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MenuCart::class.java)
                        context?.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                cartAddButton.setOnClickListener{
                    val isAdded = control.AddMenuQuantityOnCart(menu.menuId)

                    if (isAdded) {
                        Toast.makeText(context, "${menu.name} Added", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MenuCart::class.java)
                        context?.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
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