package com.example.bookacafe.view.adminDisabledUser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookacafe.R
import com.example.bookacafe.databinding.AdminDisabledUserBinding
import com.example.bookacafe.model.MemberDummy

class DisabledUserMenu: AppCompatActivity() {
    private lateinit var binding: AdminDisabledUserBinding
    private val list = ArrayList<MemberDummy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminDisabledUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        list.addAll(getListUsers())
        showRecyclerList()
    }

    fun getListUsers(): ArrayList<MemberDummy> {
        val dataName = resources.getStringArray(R.array.userName)
        val dataDescription = resources.getStringArray(R.array.userOrderHistory)
        val dataPhoto = resources.getStringArray(R.array.user_profile)

        val listHero = ArrayList<MemberDummy>()
        for (position in dataName.indices) {
            val hero = MemberDummy(
                dataName[position],
                dataDescription[position],
                dataPhoto[position]
            )
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this@DisabledUserMenu)
        val listHeroAdapter = ListUserAdapter(list)
        binding.rvUsers.adapter = listHeroAdapter
    }
}