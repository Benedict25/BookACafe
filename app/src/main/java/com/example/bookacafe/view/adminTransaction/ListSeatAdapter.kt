package com.example.bookacafe.view.adminTransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemRowSeatBinding
import com.example.bookacafe.model.adminDataDetails.TableDummy

class ListSeatAdapter(private val listSeat: ArrayList<TableDummy>) :  RecyclerView.Adapter<ListSeatAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowSeatBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listSeat.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listSeat[position])
    }

    inner class ListViewHolder(private  val binding: ItemRowSeatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(seat: TableDummy) {
            with(binding){
                seatNumberId.text = seat.tableName
                seatDescBox.text = seat.tableDesc
            }
        }
    }
}