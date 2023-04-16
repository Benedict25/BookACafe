package com.example.bookacafe.view

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bookacafe.R
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.bookacafe.controller.TableControllers
import com.example.bookacafe.model.Table
import com.example.bookacafe.model.TableTypeEnum

class TableMenu : Fragment(), View.OnClickListener {
    private lateinit var btnTableA1: Button
    private lateinit var btnTableA2: Button
    private lateinit var btnTableA3: Button
    private lateinit var btnTableA4: Button
    private lateinit var btnTableB1: Button
    private lateinit var btnTableB2: Button
    private lateinit var btnTableB3: Button
    private lateinit var btnAddToCart: Button
    private lateinit var selectedTableName: String
    private lateinit var selectedTableId: String
    private lateinit var tables: ArrayList<Table>
    private var buttons: ArrayList<Button> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.menu_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttons.clear()

        btnTableA1 = view.findViewById(R.id.btn_table_a1)
        btnTableA1.setOnClickListener(this)
        buttons.add(btnTableA1)

        btnTableA2 = view.findViewById(R.id.btn_table_a2)
        btnTableA2.setOnClickListener(this)
        buttons.add(btnTableA2)

        btnTableA3 = view.findViewById(R.id.btn_table_a3)
        btnTableA3.setOnClickListener(this)
        buttons.add(btnTableA3)

        btnTableA4 = view.findViewById(R.id.btn_table_a4)
        btnTableA4.setOnClickListener(this)
        buttons.add(btnTableA4)

        btnTableB1 = view.findViewById(R.id.btn_table_b1)
        btnTableB1.setOnClickListener(this)
        buttons.add(btnTableB1)

        btnTableB2 = view.findViewById(R.id.btn_table_b2)
        btnTableB2.setOnClickListener(this)
        buttons.add(btnTableB2)

        btnTableB3 = view.findViewById(R.id.btn_table_b3)
        btnTableB3.setOnClickListener(this)
        buttons.add(btnTableB3)

        btnAddToCart = view.findViewById(R.id.btn_add_to_cart)
        btnAddToCart.setOnClickListener(this)

        val wrappedDrawable: Drawable = DrawableCompat.wrap(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rounded_rectangle_button
            )!!
        )
        DrawableCompat.setTint(
            wrappedDrawable,
            ContextCompat.getColor(
                requireContext(),
                androidx.appcompat.R.color.material_blue_grey_800
            )
        )
        btnAddToCart.background = wrappedDrawable

        tables = TableControllers().getTableData()
        setButtonCondition(null)
    }

    override fun onClick(v: View) {
        showTables(v)
    }

    private fun setButtonCondition(selectedButton: Button?) {
        resetButtonColor()
        changeSelectedButtonColor(selectedButton)
    }

    private fun resetButtonColor() {
        for (i in 0 until tables.size) {
            if (tables[i].status == TableTypeEnum.BOOKED || tables[i].status == TableTypeEnum.BLOCKED) {
                val wrappedDrawable: Drawable = DrawableCompat.wrap(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rounded_rectangle_button
                    )!!
                )
                DrawableCompat.setTint(
                    wrappedDrawable,
                    ContextCompat.getColor(
                        requireContext(),
                        androidx.appcompat.R.color.primary_dark_material_dark
                    )
                )
                buttons[i].background = wrappedDrawable
                buttons[i].isClickable = false
            } else {
                val wrappedDrawable: Drawable = DrawableCompat.wrap(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rounded_rectangle_button
                    )!!
                )
                DrawableCompat.setTint(
                    wrappedDrawable,
                    ContextCompat.getColor(
                        requireContext(),
                        androidx.appcompat.R.color.material_blue_grey_800
                    )
                )
                buttons[i].background = wrappedDrawable
                buttons[i].isEnabled = true
                buttons[i].isClickable = true
            }
        }
    }

    private fun changeSelectedButtonColor(selectedButton: Button?) {
        val wrappedDrawable: Drawable = DrawableCompat.wrap(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rounded_rectangle_button
            )!!
        )
        DrawableCompat.setTint(
            wrappedDrawable,
            ContextCompat.getColor(
                requireContext(),
                androidx.appcompat.R.color.material_deep_teal_500
            )
        )
        if (selectedButton != null) {
            selectedButton.background = wrappedDrawable
        }
        if (selectedButton != null) {
            selectedButton.isClickable = false
        }
    }

    private fun showTables(v: View) {
        when (v.id) {
            R.id.btn_table_a1 -> {
                selectedTableId = tables[0].tableId
                selectedTableName = tables[0].tableName
                setButtonCondition(btnTableA1)

            }
            R.id.btn_table_a2 -> {
                selectedTableId = tables[1].tableId
                selectedTableName = tables[1].tableName
                setButtonCondition(btnTableA2)
            }
            R.id.btn_table_a3 -> {
                selectedTableId = tables[2].tableId
                selectedTableName = tables[2].tableName
                setButtonCondition(btnTableA3)
            }
            R.id.btn_table_a4 -> {
                selectedTableId = tables[3].tableId
                selectedTableName = tables[3].tableName
                setButtonCondition(btnTableA4)
            }
            R.id.btn_table_b1 -> {
                selectedTableId = tables[4].tableId
                selectedTableName = tables[4].tableName
                setButtonCondition(btnTableB1)
            }
            R.id.btn_table_b2 -> {
                selectedTableId = tables[5].tableId
                selectedTableName = tables[5].tableName
                setButtonCondition(btnTableB2)
            }
            R.id.btn_table_b3 -> {
                selectedTableId = tables[6].tableId
                selectedTableName = tables[6].tableName
                setButtonCondition(btnTableB3)
            }
            R.id.btn_add_to_cart -> {
                if (::selectedTableId.isInitialized && ::selectedTableName.isInitialized) {
                    showAddToCartDialog(selectedTableId, selectedTableName)
                } else {
                    showAlertDialog()
                }
            }
        }

    }

    private fun refreshTable() {
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()
    }

    private fun showAddToCartDialog(selectedTableId: String, selectedTableName: String) {
        val addToCartDialog = AlertDialog.Builder(requireContext())

        val positiveButtonClick = { _: DialogInterface, _: Int ->
            val added = TableControllers().addTableToCart(selectedTableId)
            val text: String
            if (added) {
                text = "$selectedTableName added to cart."
            } else {
                text = "You have ordered table!"
            }
            Toast.makeText(requireContext(),
                text,
                Toast.LENGTH_SHORT
            ).show()

            addToCartDialog.create().dismiss()
            refreshTable()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->

        }

        addToCartDialog.setTitle("Add to Cart")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("You chose table $selectedTableName")
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("No Table Selected")
            .setMessage("No table selected!")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("OK") {
                    _, _ -> Toast.makeText(requireContext(), "Please select a table first!", Toast.LENGTH_SHORT).show()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}