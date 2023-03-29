package com.example.bookacafe.view

import android.os.Bundle
import android.view.View
import android.widget.Button
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
    }

    override fun onClick(v: View) {
        showTables(v)
    }

    private fun showTables(v: View) {
        when (v.id) {
            R.id.btn_table_a1 -> {
                TODO("Button clicked")
            }
            R.id.btn_table_a2 -> {
                TODO("Button clicked")
            }
            R.id.btn_table_a3 -> {
                TODO("Button clicked")
            }
            R.id.btn_table_a4 -> {
                TODO("Button clicked")
            }
            R.id.btn_table_b1 -> {
                TODO("Button clicked")
            }
            R.id.btn_table_b2 -> {
                TODO("Button clicked")
            }
            R.id.btn_table_b3 -> {
                TODO("Button clicked")
            }
        }
    }


}