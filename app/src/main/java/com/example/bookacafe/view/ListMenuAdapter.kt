package com.example.bookacafe.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.MenuControllers
import com.example.bookacafe.databinding.ItemMenusBinding
import com.example.bookacafe.model.Menu
import com.squareup.picasso.Picasso

class ListMenuAdapter(private val menus : ArrayList<Menu>) : RecyclerView.Adapter<ListMenuAdapter.ListViewHolder>() {
    var onItemClick: ((Menu) -> Unit)? = null
    var context: Context? = null

    override fun onCreateViewHolder(viewGroup : ViewGroup, i : Int) : ListViewHolder {
        val binding = ItemMenusBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = menus.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(menus[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(menus[position])
        }
    }

    inner class ListViewHolder(private val binding : ItemMenusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu : Menu) {
            with(binding) {

                tvMenuName.text = menu.name
                tvMenuPrice.text = menu.price.toString()
                Picasso.get().load(menu.imagePath).into(imgMenuCover)
                btnAdd.setOnClickListener() {
                    showAddToCartDialog(menu)
                }
            }
        }
    }

    private fun showAddToCartDialog(menu: Menu) {
        val addToCartDialog = AlertDialog.Builder(context)

        val positiveButtonClick = { _: DialogInterface, _: Int ->
            val text = menu.name + " added to cart."
            val toast = Toast.makeText(context,
                text,
                Toast.LENGTH_SHORT
            )
            val layout = toast.view as LinearLayout?
            if (layout!!.childCount > 0) {
                val tv = layout!!.getChildAt(0) as TextView
                tv.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            }
            toast.show()
            MenuControllers().addMenuToCart(menu.menuId)
            addToCartDialog.create().dismiss()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->

        }


        addToCartDialog.setTitle("Add to Cart")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("You chose " + menu.name)
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }
}