package com.example.bookacafe.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.ItemRowUserBinding
import com.example.bookacafe.model.AdminMemberDetails

class AdminListUserAdapter(private val listUser: ArrayList<AdminMemberDetails>, private val context: Context, private val onPositiveClickListener: OnPositiveClickListener) :  RecyclerView.Adapter<AdminListUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pointer = listUser[position]
        holder.itemView.setOnClickListener {
            if(pointer.status == "ACTIVE") {
                showDialogDisabled(pointer.id)
            } else {
                showDialogActive(pointer.id)
            }
        }
        holder.bind(pointer)
    }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: AdminMemberDetails) {
            with(binding){
                Glide.with(itemView.context)
                    .load(user.photo)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgItemPhoto)
                tvItemName.text = user.name
                tvItemDescription.text = user.desc
                tvUserStatus.text = user.status
            }
        }
    }

    private fun showDialogDisabled(memberId: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Disabled User")
        alertDialogBuilder.setMessage("Are you sure to change the user status to INACTIVE?")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, _ -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            _, _ ->
            AdminControllers().updateUserStatus("INACTIVE",memberId)
            Toast.makeText(context, "Deactivating account succeed!", Toast.LENGTH_SHORT).show()
            onPositiveClickListener.onPositiveClick()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showDialogActive(memberId: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Activated User")
        alertDialogBuilder.setMessage("Are you sure to change the user status to ACTIVE?")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, _ -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            _, _ ->
                AdminControllers().updateUserStatus("ACTIVE",memberId)
                Toast.makeText(context, "Activating account succeed!", Toast.LENGTH_SHORT).show()
                onPositiveClickListener.onPositiveClick()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    interface OnPositiveClickListener {
        fun onPositiveClick()
    }
}