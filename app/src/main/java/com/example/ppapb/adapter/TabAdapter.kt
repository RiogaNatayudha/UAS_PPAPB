package com.example.ppapb.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ppapb.fragments.LoginFragment
import com.example.ppapb.fragments.RegisterFragment

class TabAdapter(act:AppCompatActivity) : FragmentStateAdapter(act) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> throw IllegalArgumentException("Position out of array")
        }
    }

}