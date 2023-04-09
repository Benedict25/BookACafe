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
import androidx.fragment.app.FragmentManager
import com.example.bookacafe.R
import com.example.bookacafe.view.adminTransaction.ShowTransactions


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

        val btnA1: Button = view.findViewById(R.id.buttonTablesA1)
        val btnA2: Button = view.findViewById(R.id.buttonTablesA2)
        val btnA3: Button = view.findViewById(R.id.buttonTablesA3)
        val btnA4: Button = view.findViewById(R.id.buttonTablesA4)
        val btnB1: Button = view.findViewById(R.id.buttonTablesB1)
        val btnB2: Button = view.findViewById(R.id.buttonTablesB2)
        val btnB3: Button = view.findViewById(R.id.buttonTablesB3)
        val btnLogOut: Button = view.findViewById(R.id.buttonLogOut)
        val clickableImage: ImageButton = view.findViewById(R.id.imageProfile)

        btnA1.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnA2.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnA3.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnA4.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnB1.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnB2.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnB3.setOnClickListener{
            val intent = Intent(context, ShowTransactions::class.java)
            startActivity(intent)
        }
        btnLogOut.setOnClickListener{
            val mCategoryFragment = SecondFragment()
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