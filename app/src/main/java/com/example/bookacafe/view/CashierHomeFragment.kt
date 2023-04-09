package com.example.bookacafe.view

import SecondFragment
import ThirdFragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser
import com.example.bookacafe.controller.CashierControllers
import com.example.bookacafe.view.adminTransaction.ShowTransactions
import com.example.bookacafe.view.cashierTransaction.CashierTransaction


class CashierHomeFragment :Fragment(R.layout.fragment_cashier_home) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cashier_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var cashierTextView: TextView = view.findViewById(R.id.cashier_textView)
        cashierTextView.text = ActiveUser.getFirstName() + " " + ActiveUser.getLastName()

        var btnA1: Button = view.findViewById(R.id.buttonTablesA1)
        btnA1.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnA1.text.toString())) {
                btnA1.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnA1.text.toString())
                startActivity(intent)
            }
        }

        var btnA2: Button = view.findViewById(R.id.buttonTablesA2)
        btnA2.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnA2.text.toString())) {
                btnA2.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnA2.text.toString())
                startActivity(intent)
            }
        }

        var btnA3: Button = view.findViewById(R.id.buttonTablesA3)
        btnA3.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnA3.text.toString())) {
                btnA3.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnA3.text.toString())
                startActivity(intent)
            }
        }

        var btnA4: Button = view.findViewById(R.id.buttonTablesA4)
        btnA4.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnA4.text.toString())) {
                btnA4.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnA4.text.toString())
                startActivity(intent)
            }
        }

        var btnB1: Button = view.findViewById(R.id.buttonTablesB1)
        btnB1.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnB1.text.toString())) {
                btnB1.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnB1.text.toString())
                startActivity(intent)
            }
        }

        var btnB2: Button = view.findViewById(R.id.buttonTablesB2)
        btnB2.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnB2.text.toString())) {
                btnB2.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnB2.text.toString())
                startActivity(intent)
            }
        }

        var btnB3: Button = view.findViewById(R.id.buttonTablesB3)
        btnB3.setOnClickListener{
            if (!CashierControllers().getTableInTransaction(btnB3.text.toString())) {
                btnB3.isEnabled = false
            } else {
                val intent = Intent(context, CashierTransaction::class.java)
                intent.putExtra("table_key", btnB3.text.toString())
                startActivity(intent)
            }
        }

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

    }
}