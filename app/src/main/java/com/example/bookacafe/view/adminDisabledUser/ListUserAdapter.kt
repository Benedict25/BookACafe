package com.example.bookacafe.view.adminDisabledUser

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bookacafe.R
import com.example.bookacafe.controller.AdminControllers
import com.example.bookacafe.databinding.ItemRowUserBinding
import com.example.bookacafe.model.adminDataDetails.MemberDummy

class ListUserAdapter(private val listUser: ArrayList<MemberDummy>, private val context: Context, private val onPositiveClickListener: OnPositiveClickListener) :  RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    @SuppressLint("ResourceAsColor")
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
        fun bind(user: MemberDummy) {
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
        alertDialogBuilder.setMessage("Yakin untuk mengganti status user menjadi INACTIVE??")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, which -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            dialog, which ->
            AdminControllers().updateUserStatus("INACTIVE",memberId)
            Toast.makeText(context, "Menonaktifkan akun berhasil!!", Toast.LENGTH_SHORT).show()
            onPositiveClickListener.onPositiveClick()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showDialogActive(memberId: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Activated User")
        alertDialogBuilder.setMessage("Yakin untuk mengganti status user menjadi ACTIVE??")
        alertDialogBuilder.setNegativeButton("No") {
            dialog, which -> dialog.dismiss()
        }
        alertDialogBuilder.setPositiveButton("Yes") {
            dialog, which ->
            AdminControllers().updateUserStatus("ACTIVE",memberId)
            Toast.makeText(context, "Pengaktifan akun berhasil!!", Toast.LENGTH_SHORT).show()
            onPositiveClickListener.onPositiveClick()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    interface OnPositiveClickListener {
        fun onPositiveClick()
    }
}