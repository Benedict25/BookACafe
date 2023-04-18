package com.example.bookacafe.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemBillFnbRowBinding
import com.example.bookacafe.model.Menu
import java.text.DecimalFormat

class BillFnBAdapter(private val listFnB: ArrayList<Menu>, private val listFnBQuantity: ArrayList<Int>): RecyclerView.Adapter<BillFnBAdapter.ListViewHolder>() {
    private val formatter = DecimalFormat("#,###")
    inner class ListViewHolder(private val binding: ItemBillFnbRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu, i: Int){

            with(binding){
                var totalMenuPrice = menu.price * i

                tvBillMenuName.text = menu.name
                tvBillMenuPrice.text = "Rp"+formatter.format(menu.price)+",-"
                tvBillMenuAmount.text = "x"+i.toString()
                tvBillTotalMenuPrice.text = "Rp"+formatter.format(totalMenuPrice)+",-"
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
        holder.bind(listFnB[position], listFnBQuantity[position])
    }
}