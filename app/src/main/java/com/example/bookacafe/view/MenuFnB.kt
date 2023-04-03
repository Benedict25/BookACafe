package com.example.bookacafe.view

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.databinding.MenuFnbBinding
import com.example.bookacafe.model.ListMenuAdapter
import com.example.bookacafe.model.Menu
import com.google.android.material.tabs.TabLayout

class MenuFnB : AppCompatActivity(), View.OnClickListener {

    private var tabLayout: TabLayout? = null
    private lateinit var binding: MenuFnbBinding
    private val listMenu = ArrayList<Menu>()
    private val menuType: Array<String> = arrayOf("Food", "Beverages")
    private lateinit var menuId: Array<String>
    private lateinit var menuName: Array<String>
    private lateinit var menuPrice: IntArray
    private lateinit var menuCover: TypedArray
    private var selectedTab: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuFnbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tab Layout & View Pager (Genre)
        tabLayout = findViewById(R.id.tl_menus)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(menuType[0]))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(menuType[1]))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                listMenu.clear()
                selectedTab = tab.position
                getListMenus(selectedTab)
                showMenus()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        // Show Books
        getListMenus(selectedTab)
        showMenus()
    }

    override fun onClick(p0: View?) {

    }

    private fun getListMenus(selectedTab: Int) : ArrayList<Menu> {
        when (selectedTab) {
            0 -> {
                // MASIH MANUAL
                menuId = resources.getStringArray(R.array.food_menus_id)
                menuName = resources.getStringArray(R.array.foodName)
                menuPrice = resources.getIntArray(R.array.foodPrice)
                menuCover = resources.obtainTypedArray(R.array.foodPict)
            }
            1 -> {
                // MASIH MANUAL
                menuId = resources.getStringArray(R.array.beverage_menus_id)
                menuName = resources.getStringArray(R.array.beverageName)
                menuPrice = resources.getIntArray(R.array.beveragePrice)
                menuCover = resources.obtainTypedArray(R.array.beveragePict)
            }
        }

        for (position in menuName.indices) {
            val menu = Menu(
                menuId[position],
                menuName[position],
                menuPrice[position],
                menuCover.getResourceId(position, -1),
            )
            listMenu.add(menu)
        }
        return listMenu
    }
    fun showMenus() {
        binding.rvMenus.layoutManager = GridLayoutManager(this, 2)
        val listMenuAdapter = ListMenuAdapter(listMenu)
        binding.rvMenus.adapter = listMenuAdapter

    }
//    fun showMenuDetails() {}
}