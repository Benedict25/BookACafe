package com.example.bookacafe.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.databinding.ItemBookBinding
import com.example.bookacafe.databinding.ItemMenusBinding

class ListMenuAdapter (private val listMenus: ArrayList<Menu>): RecyclerView.Adapter<ListMenuAdapter.ListViewHolder>(
) {
    var onItemClick: ((Menu) -> Unit)? = null
    class ListViewHolder(private val binding : ItemMenusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu : Menu) {
            with(binding) {
                tvMenuName.text = menu.name
                tvMenuPrice.text = menu.price.toString()
                imgMenuCover.setImageResource(menu.imagePath)
                btnAdd.setOnClickListener() {
                    TODO("Add to Cart")
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int): ListViewHolder {
        val binding = ItemMenusBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMenus.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMenus[position])
    }

}