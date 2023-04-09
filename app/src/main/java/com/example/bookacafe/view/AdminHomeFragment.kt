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
import com.example.bookacafe.view.adminDisabledUser.DisabledUserMenu
import com.example.bookacafe.view.adminTransaction.ShowTransactions


class AdminHomeFragment :Fragment(R.layout.fragment_admin_home) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDisableUser: Button = view.findViewById(R.id.buttonDisableUser)
        val btnShowTrans: Button = view.findViewById(R.id.buttonShowAllTransaction)
        val btnLogOut: Button = view.findViewById(R.id.buttonLogOut)
        val clickableImage: ImageButton = view.findViewById(R.id.imageProfile)
        btnDisableUser.setOnClickListener{
            val intent = Intent(context, DisabledUserMenu::class.java)
            startActivity(intent)
        }
        btnShowTrans.setOnClickListener{
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