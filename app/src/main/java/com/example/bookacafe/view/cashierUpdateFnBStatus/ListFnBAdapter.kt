package com.example.bookacafe.view.cashierUpdateFnBStatus

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.databinding.ItemRowFnbNotServedBinding
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail

class ListFnBAdapter(private val listFnB: ArrayList<CashierMenuDetail>, private val transactionId: String, private val context: Context, private val onPositiveClickListener: OnPositiveClickListener) :  RecyclerView.Adapter<ListFnBAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowFnbNotServedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFnB.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pointer = listFnB[position]
        holder.itemView.setOnClickListener {
            confirmServedMenu(pointer.menuId)
        }
        holder.bind(pointer)
    }

    inner class ListViewHolder(private val binding: ItemRowFnbNotServedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: CashierMenuDetail) {
            with(binding){
                tvItemName.text = menu.menuName
                tvItemDescription.text = "Quantity: " + menu.menuQuantity
                tvUserStatus.text = "NOT_SERVED"
            }
        }
    }

    private fun confirmServedMenu(menuId: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Serve Menu")
        alertDialogBuilder.setMessage("Serve the menu?")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, _ -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            _, _ ->
                CashierControllers().updateDetailTransactionStatusMenu(transactionId, menuId)
                Toast.makeText(context, "Menu has been served!", Toast.LENGTH_SHORT).show()
                onPositiveClickListener.onPositiveClick()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    interface OnPositiveClickListener {
        fun onPositiveClick()
    }
}