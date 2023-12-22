package com.example.ppapb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.ppapb.R
import com.example.ppapb.adapter.TabAdapter
import com.example.ppapb.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var tab_layout : TabLayout
    private lateinit var view_pager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tab_layout = findViewById(R.id.tab_layout)
        view_pager = findViewById(R.id.view_pager)

        val selectedTab = intent.getIntExtra("SELECTED_TAB", 0)
        with(binding) {
            view_pager.adapter = TabAdapter(this@LoginActivity)
            TabLayoutMediator(tab_layout, view_pager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Login"
                    1 -> "Register"
                    else -> ""
                }
            }.attach()
            view_pager.setCurrentItem(selectedTab, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_loginregister, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_login -> {
                Toast.makeText(this@LoginActivity,"Login", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_register -> {
                Toast.makeText(this@LoginActivity,"Register", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}