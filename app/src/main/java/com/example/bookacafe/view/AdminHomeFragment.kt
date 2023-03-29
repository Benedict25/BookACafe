package com.example.bookacafe.view

import SecondFragment
import ThirdFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import com.example.bookacafe.R


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
        btnShowTrans.setOnClickListener{
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