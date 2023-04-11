package com.example.bookacafe.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.TransactionControllers
import com.example.bookacafe.databinding.BillScreenBinding
import com.example.bookacafe.model.*
import com.example.bookacafe.view.cashierTransaction.CashierBookAdapter
import com.example.bookacafe.view.cashierUpdateFnBStatus.ListFnBAdapter
import java.lang.System.currentTimeMillis
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

class BillActivity : AppCompatActivity(), View.OnClickListener {


    //    var transaction = intent.getParcelableArrayListExtra<Transaction>("EXTRA_TRANSACTION")
    private lateinit var binding: BillScreenBinding
    private lateinit var btn_pay: Button
    private lateinit var tv_total_order: TextView
    private lateinit var tv_table: Button
    private lateinit var transaction: Transaction
    private lateinit var dialog: Dialog
    private var totalPayment: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BillScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        tv_total_order = findViewById(R.id.tv_total_order)
        btn_pay = findViewById(R.id.btn_pay_order)

        val transactionId = intent.getStringExtra("transaction_id")

        if (transactionId != null) {
            Log.d("TAG", "Transactionnya ada")
            transaction = TransactionControllers.GetTransactionDetail(transactionId)
            Log.d("TAG", "Transaction "+transaction.status)
            binding.rvBillMenu.setHasFixedSize(true)
            showRecyclerList()

            var totalBookPayment = getTotalMenuPayment()
            var totalTablePayment = getTotalTablePayment()
            totalPayment = totalBookPayment + totalTablePayment
            tv_total_order.text = "Total: " + totalPayment.toString()
            Log.d("TAG", transaction.status.toString())

            if (transaction.status == TransactionEnum.PENDING) {
                Log.d("TAG", "Buttonnya PENDING")
                btn_pay.text = "PENDING..."
                btn_pay.isEnabled = false
                btn_pay.isClickable = false
            } else if (transaction.status == TransactionEnum.PAID) {
                Log.d("TAG", "bUTTONNYA PAID")
                btn_pay.visibility = View.INVISIBLE
            }
            btn_pay.setOnClickListener(this)

        } else {
            Log.d("TAG", "Transactionnya ga ada")
            finish()
            val intent = Intent(this, MenuProfile::class.java)
            startActivity(intent)
        }
    }

    private fun getTotalTablePayment(): Int {

        var total = 0
        var ts: Timestamp = transaction.checkedIn

        var checkedIn = transaction.checkedIn
        var checkedOut = ts.time
        var totalHours = checkedOut - checkedIn.time

        var hargaPerJam = 10000
        var jamPertama = 2
        var selisihJam =
            ceil(totalHours.toDouble() / (1000 * 60 * 60)).toInt() // konversi dari mili ke jam
        if (selisihJam <= jamPertama) {
            total = hargaPerJam
        } else {
            total = hargaPerJam + (selisihJam - jamPertama) * hargaPerJam
        }
        Log.d("TAG", "Totalnya ada")
        return total

    }

    private fun getTotalMenuPayment(): Int {
        var total = 0
        for (i in transaction.menus.indices) {
            total += transaction.menus[i].price * transaction.menuQuantities[i]
        }

        Log.d("TAG", "Total Menumya ada")

        return total
    }

    private fun showRecyclerList() {
        binding.rvBillMenu.layoutManager = LinearLayoutManager(this)
        val listMenuAdapter = BillFnBAdapter(transaction.menus, transaction.menuQuantities)
        binding.rvBillMenu.adapter = listMenuAdapter

        binding.rvBillBooks.layoutManager = LinearLayoutManager(this)
        val listBookAdapter = CashierBookAdapter(transaction.books)
        binding.rvBillBooks.adapter = listBookAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_pay_order -> {
                Log.d("TAG", "Klik Button pay")
                showOrderDialog()
            }
        }
    }

    private fun showOrderDialog() {
        dialog = Dialog(this@BillActivity)
        Log.d("TAG", "Masuk show order dialog")
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            val userPaid = TransactionControllers.UpdateStatusToPending(transaction.transactionId)
            val text: String

            if (userPaid) {
                text =
                    "Thank you for your payment. Please wait the cashier to confirm your transaction."
                btn_pay.setText("PENDING...")
                btn_pay.isClickable = false
                btn_pay.isEnabled = false
            } else {
                text = "Whoops. Something wrong with your payment. Please ask cashier"
            }

            Toast.makeText(this@BillActivity, text, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val memberPayDialog = AlertDialog.Builder(this@BillActivity)
        memberPayDialog.setTitle("Payment")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("Are you want to pay? Please go to cashier.")
            .setPositiveButton(
                "Paid",
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            .setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener(function = negativeButtonClick)
            )
        memberPayDialog.show()
    }


}
