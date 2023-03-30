package com.example.bookacafe.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bookacafe.R


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_table)

        btnTableA1 = findViewById(R.id.btn_table_a1)
        btnTableA1.setOnClickListener(this)

        btnTableA2 = findViewById(R.id.btn_table_a2)
        btnTableA2.setOnClickListener(this)

        btnTableA3 = findViewById(R.id.btn_table_a3)
        btnTableA3.setOnClickListener(this)

        btnTableA4 = findViewById(R.id.btn_table_a4)
        btnTableA4.setOnClickListener(this)

        btnTableB1 = findViewById(R.id.btn_table_b1)
        btnTableB1.setOnClickListener(this)

        btnTableB2 = findViewById(R.id.btn_table_b2)
        btnTableB2.setOnClickListener(this)

        btnTableB3 = findViewById(R.id.btn_table_b3)
        btnTableB3.setOnClickListener(this)

        btnAddToCart = findViewById(R.id.btn_add_to_cart)
        btnAddToCart.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        showTables(v)
    }

    private fun resetAllButton() {
        btnTableA1.isEnabled = true
        btnTableA1.isClickable = true
        btnTableA2.isEnabled = true
        btnTableA2.isClickable = true
        btnTableA3.isEnabled = true
        btnTableA3.isClickable = true
        btnTableA4.isEnabled = true
        btnTableA4.isClickable = true
        btnTableB1.isEnabled = true
        btnTableB1.isClickable = true
        btnTableB2.isEnabled = true
        btnTableB2.isClickable = true
        btnTableB3.isEnabled = true
        btnTableB3.isClickable = true
    }

    private fun disableSelectedButton(selectedButton: Button) {
        selectedButton.isEnabled = false
        selectedButton.isClickable = false
    }

    private fun showInfoDialog(tableName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Table Selected")
            .setMessage("Table $tableName selected!")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setPositiveButton("OK") {
                    dialog, id -> Toast.makeText(applicationContext, "Table $tableName added to cart.", Toast.LENGTH_SHORT).show()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Table Selected")
            .setMessage("No table selected!")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("OK") {
                    dialog, id -> Toast.makeText(applicationContext, "Please select a table first!", Toast.LENGTH_SHORT).show()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showTables(v: View) {
        when (v.id) {
            R.id.btn_table_a1 -> {
                selectedTable = "A1"
                resetAllButton()
                disableSelectedButton(btnTableA1)
            }
            R.id.btn_table_a2 -> {
                selectedTable = "A2"
                resetAllButton()
                disableSelectedButton(btnTableA2)
            }
            R.id.btn_table_a3 -> {
                selectedTable = "A3"
                resetAllButton()
                disableSelectedButton(btnTableA3)
            }
            R.id.btn_table_a4 -> {
                selectedTable = "A4"
                resetAllButton()
                disableSelectedButton(btnTableA4)
            }
            R.id.btn_table_b1 -> {
                selectedTable = "B1"
                resetAllButton()
                disableSelectedButton(btnTableB1)
            }
            R.id.btn_table_b2 -> {
                selectedTable = "B2"
                resetAllButton()
                disableSelectedButton(btnTableB2)
            }
            R.id.btn_table_b3 -> {
                selectedTable = "B3"
                resetAllButton()
                disableSelectedButton(btnTableB3)
            }
            R.id.btn_add_to_cart -> {
                if (::selectedTable.isInitialized) {
                    showInfoDialog(selectedTable)
                } else {
                    showAlertDialog()
                }
            }
        }
    }


}