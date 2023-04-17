package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemBillFnbRowBinding
import com.example.bookacafe.model.CashierMenuDetails

class CashierBillMenuAdapter(private val listFnB: ArrayList<CashierMenuDetails>): RecyclerView.Adapter<CashierBillMenuAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemBillFnbRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: CashierMenuDetails){
            with(binding){
                tvBillMenuName.text = menu.menuName
                tvBillMenuPrice.text = "Rp"+menu.menuPrice
                tvBillMenuAmount.text = "x"+menu.menuQuantity
                tvBillTotalMenuPrice.text = "Rp"+menu.menuFinalPrice
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