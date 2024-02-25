package com.example.eletriccarapp.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eletriccarapp.presentation.fragments.CarFragment
import com.example.eletriccarapp.presentation.fragments.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CarFragment()
            1 -> FavoritesFragment()
            else -> CarFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}