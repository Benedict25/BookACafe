package com.example.bookacafe.view.cashierTransaction

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.databinding.ActivityCashierTransactionBinding
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.view.CashierActivity
import com.example.bookacafe.view.CashierHomeFragment
import java.text.DecimalFormat
import java.time.LocalDate

class CashierTransaction() : AppCompatActivity() {
    private lateinit var binding: ActivityCashierTransactionBinding
    private val menuList = ArrayList<CashierMenuDetail>()
    private val bookList = ArrayList<Book>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashierTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tableName = intent.getStringExtra("table_key")
        if (tableName != null) {
            val tableData = CashierControllers().getTableData(tableName)
            binding.tvBillSeatdisplay.text = tableData.tableName
            binding.tvBillSeatname.text = "Kursi " + tableData.tableName
            binding.tvBillSeatprice.text = "Rp"+tableData.tableDesc+",-"
            binding.dateOrdered.text = "Ordered at " + LocalDate.now()
            print(tableName)
            binding.totalOrderedCost.text = "Total Ordered: Rp"+DecimalFormat("#,###").format(CashierControllers().getTableCosts(tableName))

            binding.rvBillMenu.setHasFixedSize(true)
            menuList.addAll(CashierControllers().getOrderedMenuData(tableName))
            bookList.addAll(CashierControllers().getOrderedBookData(tableName))
            showRecyclerList()

            binding.btnPrintBill.setOnClickListener {
                //nanti ada pop up notif deh kek <yakin bang?> kalo iya baru gas
                binding.btnPrintBill.isEnabled = false
            }

            binding.btnFinishedTransaction.setOnClickListener {
                //nanti ada pop up notif deh kek <yakin bang?> kalo iya baru gas
                //nanti update transaction user nya jadi Paid
                //trus info makasih makasih an lah
                finish()
                val intent = Intent(this, CashierActivity::class.java)
                startActivity(intent)
            }
        } else {
            finish()
            val intent = Intent(this, CashierActivity::class.java)

            startActivity(intent)
        }

    }

    private fun showRecyclerList() {
        binding.rvBillMenu.layoutManager = LinearLayoutManager(this)
        val listMenuAdapter = CashierBillMenuAdapter(menuList)
        binding.rvBillMenu.adapter = listMenuAdapter

        binding.rvBillBooks.layoutManager = LinearLayoutManager(this)
        val listBookAdapter = CashierBookAdapter(bookList)
        binding.rvBillBooks.adapter = listBookAdapter
    }
}