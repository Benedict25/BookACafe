package com.example.bookacafe.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.controller.MenuControllers
import com.example.bookacafe.databinding.MenuFnbBinding
import com.example.bookacafe.model.Menu
import com.google.android.material.tabs.TabLayout

class MenuFnB : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MenuFnbBinding
    private lateinit var searchView: SearchView
    private var tabLayout: TabLayout? = null
    private var menus = ArrayList<Menu>()
    private var menuNames = ArrayList<String>()
    private var selectedTab: Int = 0
    private val menuType: Array<String> = arrayOf("FOOD", "BEVERAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuFnbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show Menus
        getListMenus(selectedTab, "")
        showMenus()

        // Search View (per Type)
        searchView = findViewById(R.id.search_view_menus)
        for (menu in menus) {
            menuNames.add(menu.name)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (menuNames.contains(query)) {
                    getListMenus(selectedTab, query.toString())
                    showMenus()
                } else {
                    Toast.makeText(
                        this@MenuFnB,
                        "No menu found.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getListMenus(selectedTab, newText.toString())
                showMenus()
                return false
            }
        })

        // Tab Layout & View Pager (Genre)
        tabLayout = findViewById(R.id.tl_menus)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(menuType[0]))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(menuType[1]))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                menus.clear()
                selectedTab = tab.position
                searchView.setQuery("", false)
                var queryHint = ""
                when (selectedTab) {
                    0 -> {
                        queryHint = "Let's eat boom boom burgir!"
                    }
                    1 -> {
                        queryHint = "Refresh your day with Merry Squash"
                    }
                }
                searchView.queryHint = queryHint

                getListMenus(selectedTab, "")
                showMenus()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onClick(p0: View?) {

    }

    private fun getListMenus(selectedTab: Int, inputText: String): ArrayList<Menu> {
        when (selectedTab) {
            0 -> {
                menus = MenuControllers().getMenuData(menuType[0].lowercase(), inputText)
            }
            1 -> {
                menus = MenuControllers().getMenuData(menuType[1].lowercase(), inputText)
            }
        }
        menuNames.clear()
        for (menu in menus) {
            menuNames.add(menu.name)
        }
        return menus
    }

    fun showMenus() {
        binding.rvMenus.layoutManager = GridLayoutManager(this, 2)
        val listMenuAdapter = ListMenuAdapter(menus)
        binding.rvMenus.adapter = listMenuAdapter

    }
}