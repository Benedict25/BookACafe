package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ListCartMenuBinding
import com.example.bookacafe.model.Cart
import com.example.bookacafe.model.Menu
import com.squareup.picasso.Picasso

class ListMenuCartAdapter(private val cart: Cart) : RecyclerView.Adapter<ListMenuCartAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ListCartMenuBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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
            }
        }
        fun bindQuantity(quantity: Int) {
            with(binding) {
                cartFnbQuantity.text = quantity.toString()
            }
        }
    }

}