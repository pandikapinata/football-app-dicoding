package com.example.pandu.finalproject.team.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.pandu.finalproject.model.Team
import com.example.pandu.finalproject.team.detail.OverviewTeamFragment
import com.example.pandu.finalproject.team.detail.PlayerTeamFragment

class TeamPagerAdapter(fragmentManager: FragmentManager, val teamItem : Team): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment {
        when (p0){
            0 -> {
                val bundle = Bundle()
                val overviews = OverviewTeamFragment()
                bundle.putParcelable("teamExtra", teamItem)
                overviews.arguments = bundle
                return overviews
            }
            else -> {
                val bundle = Bundle()
                val players = PlayerTeamFragment()
                bundle.putParcelable("teamExtra", teamItem)
                players.arguments = bundle
                return players
            }
        }

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Overview"
            else -> "Players"
        }
    }
}