package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookacafe.R
import com.example.bookacafe.databinding.ItemRowBeverageBinding
import com.example.bookacafe.model.AdminMenuDetails

class AdminListBeverageAdapter(private val listBeverage: ArrayList<AdminMenuDetails>) :  RecyclerView.Adapter<AdminListBeverageAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowBeverageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBeverage.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBeverage[position])
    }

    inner class ListViewHolder(private val binding: ItemRowBeverageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: AdminMenuDetails) {
            with(binding){
                Glide.with(itemView)
                    .load(menu.menuPict)
                    .apply(RequestOptions().override(55, 55))
                    .placeholder(R.drawable.foods_beverages)
                    .into(imgBeveragePhoto)
                tvItemName.text = menu.menuName
                tvItemDescription.text = menu.menuDesc
            }
        }
    }
}