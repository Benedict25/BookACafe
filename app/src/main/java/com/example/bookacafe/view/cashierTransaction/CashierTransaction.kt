package com.example.bookacafe.view.cashierTransaction

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.databinding.ActivityCashierTransactionBinding
import com.example.bookacafe.model.Book
import com.example.bookacafe.model.adminDataDetails.CashierMenuDetail
import com.example.bookacafe.model.adminDataDetails.TableDummy
import com.example.bookacafe.view.CashierActivity
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

//            val alertDialogBuilder = AlertDialog.Builder(this)
            binding.btnPrintBill.setOnClickListener {
                dialogBoxPrintBill(tableData)
            }

            binding.btnFinishedTransaction.setOnClickListener {
                dialogBoxFinishedTransaction(tableData)
            }
//                dialogBoxFinishedTransaction(tableData)
//                //trus info makasih makasih an lah


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

    // Menampilkan AlertDialog pertama
    private fun dialogBoxPrintBill(table:TableDummy) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Print Bill")
        alertDialogBuilder.setMessage("Lanjutkan proses pencetakan bill??")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Aksi yang dijalankan ketika tombol "OK"
            //realnya kalo ngeprint ya bawa datanya kan :))
            dialog.dismiss()
            binding.btnPrintBill.isEnabled = false
            Toast.makeText(this, "Bill sudah dicetak!", Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Aksi yang dijalankan ketika tombol "Cancel" ditekan
            dialog.dismiss()
            Toast.makeText(this, "Alert Dialog Closed!", Toast.LENGTH_SHORT).show()
        }
        val alertDialog1 = alertDialogBuilder.create()
        alertDialog1.show()
    }

    // Menampilkan AlertDialog kedua
    private fun dialogBoxFinishedTransaction(table: TableDummy) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Finished Transaction")
        alertDialogBuilder.setMessage("Selesaikan Transaksi??")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Aksi yang dijalankan ketika tombol "Yes" ditekan
            dialog.dismiss()

            CashierControllers().updateTransactionStatus(table.tableId)
            Toast.makeText(this, "Transaksi pada meja "+ table.tableName + " sudah selesai!", Toast.LENGTH_SHORT).show()

            finish()
            val intent = Intent(this, CashierActivity::class.java)
            startActivity(intent)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Aksi yang dijalankan ketika tombol "No" ditekan
            dialog.dismiss()
            Toast.makeText(this, "Alert Dialog Closed!", Toast.LENGTH_SHORT).show()
        }
        val alertDialog2 = alertDialogBuilder.create()
        alertDialog2.show()
    }
}