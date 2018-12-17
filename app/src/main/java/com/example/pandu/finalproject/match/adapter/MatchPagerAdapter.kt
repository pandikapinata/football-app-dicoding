package com.example.pandu.finalproject.match.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.pandu.finalproject.match.LastMatchFragment
import com.example.pandu.finalproject.match.NextMatchFragment

class MatchPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment {
        return when (p0){
            0 -> {
                LastMatchFragment()
            }
            else -> {
                return NextMatchFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Last Match"
            else -> "Next Match"
        }
    }
}