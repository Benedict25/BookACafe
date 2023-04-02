package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookacafe.databinding.ItemBillFnbRowBinding
import com.example.bookacafe.model.Menu

class BillFnBAdapter(private val listFnB: ArrayList<Menu>): RecyclerView.Adapter<BillFnBAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemBillFnbRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu){
            with(binding){
                tvBillMenuName.text = menu.name
                tvBillMenuPrice.text = menu.price.toString()
                tvBillMenuAmount.text = "x1"
                tvBillTotalMenuPrice.text = menu.price.toString()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemBillFnbRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFnB.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFnB[position])
    }
}