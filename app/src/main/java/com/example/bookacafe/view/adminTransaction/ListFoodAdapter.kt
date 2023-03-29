package com.example.bookacafe.view.adminTransaction

import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookacafe.R
import com.example.bookacafe.databinding.ItemRowFoodBinding
import com.example.bookacafe.model.MenuDummy
import java.io.File

class ListFoodAdapter(private val listFood: ArrayList<MenuDummy>) :  RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowFoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFood[position])
    }

    inner class ListViewHolder(private val binding: ItemRowFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: MenuDummy) {
            with(binding){
                Glide.with(itemView)
                    .load(menu.menuPict)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgFoodPhoto)
                tvItemName.text = menu.menuName
                tvItemDescription.text = menu.menuDesc
            }
        }
    }
}