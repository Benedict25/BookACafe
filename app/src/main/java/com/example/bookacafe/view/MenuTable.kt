package com.example.bookacafe.view

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.bookacafe.R
import com.example.bookacafe.controller.TableControllers
import com.example.bookacafe.model.Table
import com.example.bookacafe.model.TableTypeEnum

class MenuTable : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnTableA1: Button
    private lateinit var btnTableA2: Button
    private lateinit var btnTableA3: Button
    private lateinit var btnTableA4: Button
    private lateinit var btnTableB1: Button
    private lateinit var btnTableB2: Button
    private lateinit var btnTableB3: Button
    private lateinit var btnAddToCart: Button
    private lateinit var selectedTable: String
    private lateinit var tables: ArrayList<Table>
    private var buttons: ArrayList<Button> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_table)

        btnTableA1 = findViewById(R.id.btn_table_a1)
        btnTableA1.setOnClickListener(this)
        buttons.add(btnTableA1)

        btnTableA2 = findViewById(R.id.btn_table_a2)
        btnTableA2.setOnClickListener(this)
        buttons.add(btnTableA2)

        btnTableA3 = findViewById(R.id.btn_table_a3)
        btnTableA3.setOnClickListener(this)
        buttons.add(btnTableA3)

        btnTableA4 = findViewById(R.id.btn_table_a4)
        btnTableA4.setOnClickListener(this)
        buttons.add(btnTableA4)

        btnTableB1 = findViewById(R.id.btn_table_b1)
        btnTableB1.setOnClickListener(this)
        buttons.add(btnTableB1)

        btnTableB2 = findViewById(R.id.btn_table_b2)
        btnTableB2.setOnClickListener(this)
        buttons.add(btnTableB2)

        btnTableB3 = findViewById(R.id.btn_table_b3)
        btnTableB3.setOnClickListener(this)
        buttons.add(btnTableB3)

        btnAddToCart = findViewById(R.id.btn_add_to_cart)
        btnAddToCart.setOnClickListener(this)

        val wrappedDrawable: Drawable = DrawableCompat.wrap(
            ContextCompat.getDrawable(
                this@MenuTable,
                R.drawable.rounded_rectangle_button
            )!!
        )
        DrawableCompat.setTint(
            wrappedDrawable,
            ContextCompat.getColor(
                this@MenuTable,
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
                        this@MenuTable,
                        R.drawable.rounded_rectangle_button
                    )!!
                )
                DrawableCompat.setTint(
                    wrappedDrawable,
                    ContextCompat.getColor(
                        this@MenuTable,
                        androidx.appcompat.R.color.primary_dark_material_dark
                    )
                )
                buttons[i].background = wrappedDrawable
                buttons[i].isClickable = false
            } else {
                val wrappedDrawable: Drawable = DrawableCompat.wrap(
                    ContextCompat.getDrawable(
                        this@MenuTable,
                        R.drawable.rounded_rectangle_button
                    )!!
                )
                DrawableCompat.setTint(
                    wrappedDrawable,
                    ContextCompat.getColor(
                        this@MenuTable,
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
                this@MenuTable,
                R.drawable.rounded_rectangle_button
            )!!
        )
        DrawableCompat.setTint(
            wrappedDrawable,
            ContextCompat.getColor(
                this@MenuTable,
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
                selectedTable = tables[0].tableName
                setButtonCondition(btnTableA1)

            }
            R.id.btn_table_a2 -> {
                selectedTable = tables[1].tableName
                setButtonCondition(btnTableA2)
            }
            R.id.btn_table_a3 -> {
                selectedTable = tables[2].tableName
                setButtonCondition(btnTableA3)
            }
            R.id.btn_table_a4 -> {
                selectedTable = tables[3].tableName
                setButtonCondition(btnTableA4)
            }
            R.id.btn_table_b1 -> {
                selectedTable = tables[4].tableName
                setButtonCondition(btnTableB1)
            }
            R.id.btn_table_b2 -> {
                selectedTable = tables[5].tableName
                setButtonCondition(btnTableB2)
            }
            R.id.btn_table_b3 -> {
                selectedTable = tables[6].tableName
                setButtonCondition(btnTableB3)
            }
            R.id.btn_add_to_cart -> {
                if (::selectedTable.isInitialized) {
                    showAddToCartDialog(selectedTable)
                } else {
                    showAlertDialog()
                }
            }
        }

    }

    private fun showAddToCartDialog(selectedTable: String) {
        val addToCartDialog = AlertDialog.Builder(this@MenuTable)

        val positiveButtonClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this@MenuTable,
                "$selectedTable added to cart.",
                Toast.LENGTH_SHORT
            ).show()
            TableControllers().addTableToCart(selectedTable)
            addToCartDialog.create().dismiss()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->

        }

        addToCartDialog.setTitle("Add to Cart")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("You chose table $selectedTable")
            .setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
            .setNegativeButton("No", DialogInterface.OnClickListener(function = negativeButtonClick))
        addToCartDialog.show()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Table Selected")
            .setMessage("No table selected!")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("OK") {
                    _, _ -> Toast.makeText(applicationContext, "Please select a table first!", Toast.LENGTH_SHORT).show()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}