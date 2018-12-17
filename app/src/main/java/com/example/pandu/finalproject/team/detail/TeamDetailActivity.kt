package com.example.pandu.finalproject.team.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.db.db
import com.example.pandu.finalproject.model.Team
import com.example.pandu.finalproject.team.adapter.TeamPagerAdapter
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamDetailActivity : AppCompatActivity() {

    private lateinit var teamExtra: Team
    private var menuItem: Menu? = null
    private var toolbarTeam: Toolbar? = null
    private var isFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        teamExtra = intent.getParcelableExtra("teamExtra")
        val teamDetailActivity = TeamPagerAdapter(supportFragmentManager, teamExtra)
        vp_team_detail.adapter = teamDetailActivity
        tl_team_detail.setupWithViewPager(vp_team_detail)

        toolbarTeam = toolbar_team_detail
        setSupportActionBar(toolbarTeam)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        teamExtra.idTeam?.let { favState(it) }
        setTeamDetail()
    }

    private fun setTeamDetail() {
        tv_team_name.text = teamExtra.strTeam
        tv_team_formed.text = teamExtra.intFormedYear
        tv_team_stadium.text = teamExtra.strStadium
        Glide.with(applicationContext).load(teamExtra.strTeamBadge).into(iv_team_detail)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        menuItem?.getItem(1)?.isVisible = false
        setFav()
        return true
    }

    private fun setFav() {
        if (isFav) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_fav -> {
                if (isFav) deleteFav() else addFav()
                isFav = !isFav
                setFav()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteFav() {
        try {
            db.use {
                delete(Team.TABLE_TEAMS, "ID_TEAM = {idTeam}",
                    "idTeam" to teamExtra.idTeam.toString())
            }
            toast("Deleted from Favorite")
        }catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun addFav() {
        try{
            db.use {
                insert(Team.TABLE_TEAMS,
                    Team.ID_TEAM to teamExtra.idTeam.toString(),
                    Team.TEAM_NAME to teamExtra.strTeam,
                    Team.TEAM_FORMED to teamExtra.intFormedYear,
                    Team.TEAM_STADIUM to teamExtra.strStadium,
                    Team.TEAM_DESC to teamExtra.strDescriptionEN,
                    Team.TEAM_LOGO to teamExtra.strTeamBadge)
            }
            toast("Added to Favorite")
        }catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun favState(idTeam: String){
        db.use {
            val selectData = select(Team.TABLE_TEAMS)
                .whereArgs("ID_TEAM = {idTeam}",
                    "idTeam" to idTeam)
            val favorites = selectData.parseList(classParser<Team>())
            if (!favorites.isEmpty()) isFav = true
        }
    }
}
