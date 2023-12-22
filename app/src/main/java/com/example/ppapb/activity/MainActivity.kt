package com.example.ppapb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ppapb.R
import com.example.ppapb.databinding.ActivityMainBinding
import com.example.ppapb.fragments.PlayingFragment
import com.example.ppapb.fragments.ProfileFragment
import com.example.ppapb.fragments.UpcomingFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(PlayingFragment())

        binding.navigasi.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_playing -> replaceFragment(PlayingFragment())
                R.id.action_upcoming -> replaceFragment(UpcomingFragment())
                R.id.action_akun -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }
}