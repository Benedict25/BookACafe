package com.example.bookacafe.view

import FirstFragment
import SecondFragment
import ThirdFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bookacafe.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        supportActionBar?.hide()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val firstFragment=FirstFragment()
        val secondFragment=SecondFragment()
        val thirdFragment=ThirdFragment()

        val fnbPage=MenuFnB()
        setCurrentFragment(firstFragment)

//        bottomNavigationView.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.icon_home->setCurrentFragment(firstFragment)
//                R.id.icon_tables->setCurrentFragment(secondFragment)
//                R.id.icon_books->setCurrentFragment(thirdFragment)
//                R.id.icon_fnb->setCurrentFragment(thirdFragment)
//                R.id.icon_cart->setCurrentFragment(thirdFragment)
//            }
//            true
//        }
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, MenuProfile::class.java)
                    startActivity(intent)
                }
                R.id.icon_tables->{
                    val intent = Intent(this, MenuTable::class.java)
                    startActivity(intent)
                }
                R.id.icon_books->{
                    val intent = Intent(this, MenuBook::class.java)
                    startActivity(intent)
                }
                R.id.icon_fnb->{
                    val intent = Intent(this, MenuFnB::class.java)
                    startActivity(intent)
                }
                R.id.icon_cart -> {
                    val intent = Intent(this, MenuCart::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}