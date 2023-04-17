package com.example.bookacafe.view

import FirstFragment
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
        val tableFragment=TableMenu()
        val bookFragment=BookMenu()
        val fnbFragment=FnBMenu()
        val cartFragment=MenuCart()
        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_tables->{
//                    val intent = Intent(this, MenuTable::class.java)
//                    startActivity(intent)
                    setCurrentFragment(tableFragment)
                }
                R.id.icon_books->{
//                    val intent = Intent(this, MenuBook::class.java)
//                    startActivity(intent)
                    setCurrentFragment(bookFragment)
                }
                R.id.icon_fnb->{
//                    val intent = Intent(this, MenuFnB::class.java)
//                    startActivity(intent)
                    setCurrentFragment(fnbFragment)
                }
                R.id.icon_cart -> {
//                    val intent = Intent(this, MenuCart::class.java)
//                    startActivity(intent)
                    setCurrentFragment(cartFragment)
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