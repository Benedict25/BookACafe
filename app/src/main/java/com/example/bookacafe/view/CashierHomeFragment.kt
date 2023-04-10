package com.example.bookacafe.view

import ThirdFragment
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.controller.TableControllers
import com.example.bookacafe.model.Table
import com.example.bookacafe.model.TableTypeEnum
import com.example.bookacafe.view.cashierTransaction.CashierTransaction
import com.example.bookacafe.view.cashierUpdateFnBStatus.CashierUpdateNotServedStatus
import java.util.*
import kotlin.collections.ArrayList


class CashierHomeFragment :Fragment(R.layout.fragment_cashier_home), View.OnClickListener {
    private lateinit var btnTableA1: Button
    private lateinit var btnTableA2: Button
    private lateinit var btnTableA3: Button
    private lateinit var btnTableA4: Button
    private lateinit var btnTableB1: Button
    private lateinit var btnTableB2: Button
    private lateinit var btnTableB3: Button
    private lateinit var tables: ArrayList<Table>
    private var buttons: ArrayList<Button> = ArrayList()

    private val mHandler = Handler()
    private lateinit var mTimer: Timer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cashier_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTimer = Timer()
        mTimer.scheduleAtFixedRate(RefreshTask(), 0, 10000)

        var cashierTextView: TextView = view.findViewById(R.id.cashier_textView)
        cashierTextView.text = "Welcome,\n" + ActiveUser.getFirstName() + " " + ActiveUser.getLastName()

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

        var btnLogOut: Button = view.findViewById(R.id.buttonLogOut)
        var clickableImage: ImageButton = view.findViewById(R.id.imageProfile)

        btnLogOut.setOnClickListener{
            //nanti set user ke null
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }

        clickableImage.setOnClickListener{
            val mCategoryFragment = ThirdFragment()
            val mFragmentManager = parentFragmentManager as FragmentManager
            mFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    mCategoryFragment,
                    ThirdFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }

        tables = TableControllers().getTableData()
        resetButtonColor()
    }

    inner class RefreshTask : TimerTask() {
        override fun run() {
            mHandler.post {
                tables = TableControllers().getTableData()
                resetButtonColor()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTimer.cancel()
    }

    private fun resetButtonColor() {

        for (i in 0 until tables.size) {
            if (tables[i].status == TableTypeEnum.AVAILABLE) {
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

    override fun onClick(v: View) {
        showTables(v)
    }

    private fun showTables(v: View) {
        when (v.id) {
            R.id.btn_table_a1 -> {
                val transactionId = CashierControllers().getTransactionId(tables[0].tableName)
                if (CashierControllers().getTableInTransaction(tables[0].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[0].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[0].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[0].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_table_a2 -> {
                val transactionId = CashierControllers().getTransactionId(tables[1].tableName)
                if (CashierControllers().getTableInTransaction(tables[1].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[1].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[1].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[1].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_table_a3 -> {
                val transactionId = CashierControllers().getTransactionId(tables[2].tableName)
                if (CashierControllers().getTableInTransaction(tables[2].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[2].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[2].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[2].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_table_a4 -> {
                val transactionId = CashierControllers().getTransactionId(tables[3].tableName)
                if (CashierControllers().getTableInTransaction(tables[3].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[3].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[3].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[3].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_table_b1 -> {
                val transactionId = CashierControllers().getTransactionId(tables[4].tableName)
                if (CashierControllers().getTableInTransaction(tables[4].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[4].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[4].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[4].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_table_b2 -> {
                val transactionId = CashierControllers().getTransactionId(tables[5].tableName)
                if (CashierControllers().getTableInTransaction(tables[5].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[5].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[5].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[5].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_table_b3 -> {
                val transactionId = CashierControllers().getTransactionId(tables[6].tableName)
                if (CashierControllers().getTableInTransaction(tables[6].tableName)) {
                    val intent = Intent(context, CashierTransaction::class.java)
                    intent.putExtra("table_key", tables[6].tableName)
                    startActivity(intent)

                } else if(CashierControllers().getNotServedMenu(transactionId)) {
                    val intent = Intent(context, CashierUpdateNotServedStatus::class.java)
                    intent.putExtra("table_key", tables[6].tableName)
                    intent.putExtra("trans_id", transactionId)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Semua pesanan dari meja " + tables[6].tableName + " sudah disajikan\nCustomer belum melakukan checkout!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}