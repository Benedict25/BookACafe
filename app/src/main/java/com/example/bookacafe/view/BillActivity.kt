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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.TransactionControllers
import com.example.bookacafe.databinding.BillScreenBinding
import com.example.bookacafe.model.*
import com.example.bookacafe.model.adminDataDetails.CashierBookDetail
import com.example.bookacafe.view.cashierTransaction.CashierBookAdapter
import java.sql.Time
import java.sql.Timestamp
import kotlin.math.ceil

class BillActivity : AppCompatActivity(), View.OnClickListener {

    //    var transaction = intent.getParcelableArrayListExtra<Transaction>("EXTRA_TRANSACTION")
    private lateinit var binding: BillScreenBinding
    private lateinit var btnPay: Button
    private lateinit var tvTotalOrder: TextView
    private lateinit var tvBillSeatDisplay: Button
    private lateinit var tvBillSeatName: TextView
    private lateinit var tvBillSeatPrice: TextView
    private lateinit var transaction: Transaction
    private lateinit var dialog: Dialog
    private var totalPayment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BillScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        tvTotalOrder = findViewById(R.id.tv_total_order)
        btnPay = findViewById(R.id.btn_pay_order)
        tvBillSeatDisplay = findViewById(R.id.tv_bill_seatdisplay)
        tvBillSeatName = findViewById(R.id.tv_bill_seatname)
        tvBillSeatPrice = findViewById(R.id.tv_bill_seatprice)

        val transactionId = intent.getStringExtra("transaction_id")

        if (transactionId != null) {
            transaction = TransactionControllers.getTransactionDetail(transactionId)
            binding.rvBillMenu.setHasFixedSize(true)
            showRecyclerList()

            var checkOutTime = getCheckOut()
            var totalBookPayment = getTotalMenuPayment()
            var (totalTablePayment, selisihJam) = getTotalTablePayment(transaction.checkedIn, checkOutTime)


            totalPayment = totalBookPayment + totalTablePayment
            tvTotalOrder.text = "Total: " + totalPayment.toString()
            tvBillSeatDisplay.text = transaction.table?.tableName
            tvBillSeatName.text = "Kursi "+transaction.table?.tableName
            tvBillSeatPrice.text = "Rp "+totalTablePayment.toString()+ ", "+selisihJam.toString()+" hours."

            if (transaction.status == TransactionEnum.PENDING) {
                btnPay.text = "PENDING..."
                btnPay.isEnabled = false
                btnPay.isClickable = false
            } else if (transaction.status == TransactionEnum.PAID) {
                btnPay.visibility = View.INVISIBLE
            } else if (transaction.status == TransactionEnum.CANCELLED) {
                btnPay.text = "CANCELLED"
                btnPay.isEnabled = false
                btnPay.isClickable = false
            }
            btnPay.setOnClickListener(this)
        } else {
            finish()
            val intent = Intent(this, MenuProfile::class.java)
            startActivity(intent)
        }
    }

    private fun getCheckOut(): Timestamp {
        if (transaction.checkedOut != null){
            return transaction.checkedOut!!
        }
        return Timestamp(System.currentTimeMillis())
    }

    private fun getTotalTablePayment(checkedIn: Timestamp, checkedOut: Timestamp): Pair<Int, Int> {
        var total = 0

        var totalHours = checkedOut.time - checkedIn.time

        Log.d("TAG","Total Jam(dalam milisekom): "+totalHours)

        var hargaPerJam = 10000
        var jamPertama = 2
        var selisihJam =
            ceil(totalHours.toDouble() / (1000 * 60 * 60)).toInt() // konversi dari mili ke jam
        if (selisihJam <= 2) {
            total = hargaPerJam
        } else {
            total = hargaPerJam + (selisihJam - jamPertama) * hargaPerJam
        }
        Log.d("TAG", "Totalnya ada: "+total)
        return Pair(total, selisihJam)

    }

    private fun getTotalMenuPayment(): Int {
        var total = 0
        for (i in transaction.menus.indices) {
            total += transaction.menus[i].price * transaction.menuQuantities[i]
        }

        return total
    }

    private fun showRecyclerList() {
        binding.rvBillMenu.layoutManager = LinearLayoutManager(this)
        val listMenuAdapter = BillFnBAdapter(transaction.menus, transaction.menuQuantities)
        binding.rvBillMenu.adapter = listMenuAdapter

        binding.rvBillBooks.layoutManager = LinearLayoutManager(this)
        val listBookAdapter = BillBookAdapter(transaction.books)
        binding.rvBillBooks.adapter = listBookAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_pay_order -> {
                showOrderDialog()
            }
        }
    }

    private fun showOrderDialog() {
        dialog = Dialog(this@BillActivity)

        val positiveButtonClick = { _: DialogInterface, _: Int ->
            val userPaid = TransactionControllers.updateStatusToPending(transaction.transactionId)

            val text: String

            if (userPaid) {
                text =
                    "Thank you for your payment. Please wait the cashier to confirm your transaction."
                btnPay.setText("PENDING...")
                btnPay.isClickable = false
                btnPay.isEnabled = false
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
