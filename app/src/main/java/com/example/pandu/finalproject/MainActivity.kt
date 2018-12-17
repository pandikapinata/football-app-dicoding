package com.example.pandu.finalproject

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.pandu.finalproject.favorite.FavoriteFragment
import com.example.pandu.finalproject.match.MatchFragment
import com.example.pandu.finalproject.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener{item ->
            when (item.itemId) {
                R.id.navigation_matchs -> {
                    val matchFragment = MatchFragment()
                    loadFragment(savedInstanceState, matchFragment)
                }
                R.id.navigation_teams -> {
                    val teamFragment = TeamFragment()
                    loadFragment(savedInstanceState, teamFragment)
                }
                R.id.navigation_favorites -> {
                    val favFragment = FavoriteFragment()
                    loadFragment(savedInstanceState, favFragment)
                }

            }
            true
        }
        navigation.selectedItemId = R.id.navigation_matchs
    }

    private fun loadFragment(savedInstanceState: Bundle?, fragment: Fragment){
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit()
        }
    }
}
