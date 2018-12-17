package com.example.pandu.finalproject.match.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import com.bumptech.glide.Glide
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.model.Team
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_match.*
import android.util.Log
import android.view.MenuItem
import com.example.pandu.finalproject.db.db
import com.example.pandu.finalproject.utils.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private var detailMatch: MutableList<Match> = mutableListOf()
    private var menuItem: Menu? = null
    private lateinit var presenterDetail: DetailMatchPresenter
    private var isFav: Boolean = false
    private var isNextMatch: String? = null
    private var MY_PERMISSIONS_REQUEST_READ_CALENDAR = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val request = APIRepository()
        val gson = Gson()
        val matchId = intent.getStringExtra("matchId")
        isNextMatch = intent.getStringExtra("alarmButton")
        presenterDetail = DetailMatchPresenter(this, request, gson)
        favState(matchId)
        presenterDetail.getMatchDetail(matchId)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        isNextorNot()
        setFav()
        return true
    }

    private fun isNextorNot() {
        if (isNextMatch == "ada") {
            menuItem?.getItem(1)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_alarm)
        } else {
            menuItem?.getItem(1)?.isVisible = false
        }
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
            R.id.add_to_calendar -> {
                requestCalendarPermission()
//                toast("Add to Calendar")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFav() {
        try {
            db.use {
                insert(
                    Match.TABLE_MATCH,
                    Match.MATCH_ID to detailMatch[0].matchId,
                    Match.HOME_ID to detailMatch[0].idHomeTeam,
                    Match.AWAY_ID to detailMatch[0].idAwayTeam,
                    Match.MATCH_DATE to detailMatch[0].dateEvent,
                    Match.MATCH_TIME to detailMatch[0].strTime,
                    Match.HOME_TEAM to detailMatch[0].strHomeTeam,
                    Match.AWAY_TEAM to detailMatch[0].strAwayTeam,
                    Match.HOME_SCORE to detailMatch[0].intHomeScore,
                    Match.AWAY_SCORE to detailMatch[0].intAwayScore,
                    Match.HOME_SHOTS to detailMatch[0].intHomeShots,
                    Match.AWAY_SHOTS to detailMatch[0].intAwayShots,
                    Match.HOME_GOAL to detailMatch[0].strHomeGoalDetails,
                    Match.AWAY_GOAL to detailMatch[0].strAwayGoalDetails,
                    Match.HOME_RED to detailMatch[0].strHomeRedCards,
                    Match.AWAY_RED to detailMatch[0].strAwayRedCards,
                    Match.HOME_YELLOW to detailMatch[0].strHomeYellowCards,
                    Match.AWAY_YELLOW to detailMatch[0].strAwayYellowCards,
                    Match.HOME_GK to detailMatch[0].strHomeLineupGoalkeeper,
                    Match.HOME_DFD to detailMatch[0].strHomeLineupDefense,
                    Match.HOME_MDF to detailMatch[0].strHomeLineupMidfield,
                    Match.HOME_FW to detailMatch[0].strHomeLineupForward,
                    Match.AWAY_GK to detailMatch[0].strAwayLineupGoalkeeper,
                    Match.AWAY_DFD to detailMatch[0].strAwayLineupDefense,
                    Match.AWAY_MDF to detailMatch[0].strAwayLineupMidfield,
                    Match.AWAY_FW to detailMatch[0].strAwayLineupForward
                )
            }
            toast("Added to Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun deleteFav() {
        try {
            db.use {
                delete(
                    Match.TABLE_MATCH, "MATCH_ID = {idMatch}",
                    "idMatch" to detailMatch[0].matchId.toString()
                )
            }
            toast("Deleted from Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun favState(idMatch: String) {
        db.use {
            val selectData = select(Match.TABLE_MATCH)
                .whereArgs(
                    "MATCH_ID = {idMatch}",
                    "idMatch" to idMatch
                )
            val favorites = selectData.parseList(classParser<Match>())
            if (!favorites.isEmpty()) isFav = true
        }
    }

    override fun showMatchDetail(match: List<Match>) {
        Log.e("DATA MATCH", match.toString())
        detailMatch.clear()
        detailMatch.addAll(match)
        tv_match_date_detail.text = toDatetoString(
            detailMatch[0].dateEvent.toString(),
            detailMatch[0].strTime.toString(),
            "EEE, dd MMMM YYYY"
        )
        tv_match_time_detail.text =
                toDatetoString(detailMatch[0].dateEvent.toString(), detailMatch[0].strTime.toString(), "HH:mm")

        tv_score_home_detail.text = detailMatch[0].intHomeScore
        tv_score_away_detail.text = detailMatch[0].intAwayScore

        tv_home_name_detail.text = detailMatch[0].strHomeTeam
        tv_away_name_detail.text = detailMatch[0].strAwayTeam

        tv_home_goal_detail.text = stringReplace(detailMatch[0].strHomeGoalDetails)
        tv_away_goal_detail.text = stringReplace(detailMatch[0].strAwayGoalDetails)

        tv_home_shots.text = detailMatch[0].intHomeShots
        tv_away_shots.text = detailMatch[0].intAwayShots

        tv_home_yc.text = countList(detailMatch[0].strHomeYellowCards)
        tv_away_yc.text = countList(detailMatch[0].strAwayYellowCards)

        tv_home_rc.text = countList(detailMatch[0].strHomeRedCards)
        tv_away_rc.text = countList(detailMatch[0].strAwayRedCards)

        tv_home_gk.text = stringReplace(detailMatch[0].strHomeLineupGoalkeeper)
        tv_away_gk.text = stringReplace(detailMatch[0].strAwayLineupGoalkeeper)

        tv_home_dfd.text = stringReplace(detailMatch[0].strHomeLineupDefense)
        tv_away_dfd.text = stringReplace(detailMatch[0].strAwayLineupDefense)

        tv_home_mdf.text = stringReplace(detailMatch[0].strHomeLineupMidfield)
        tv_away_mdf.text = stringReplace(detailMatch[0].strAwayLineupMidfield)

        tv_home_fw.text = stringReplace(detailMatch[0].strHomeLineupForward)
        tv_away_fw.text = stringReplace(detailMatch[0].strAwayLineupForward)

        presenterDetail.getHomeLogo(detailMatch[0].idHomeTeam)
        presenterDetail.getAwayLogo(detailMatch[0].idAwayTeam)

    }

    override fun showHomeLogoTeam(logoTeam: List<Team>?) {
        Glide.with(applicationContext).load(logoTeam?.get(0)?.strTeamBadge).into(iv_home_detail)
    }

    override fun showAwayLogoTeam(logoTeam: List<Team>?) {
        Glide.with(applicationContext).load(logoTeam?.get(0)?.strTeamBadge).into(iv_away_detail)
    }

    private fun requestCalendarPermission() {
        val permissionArrayList = ArrayList<String>()

        if (ContextCompat.checkSelfPermission(
                this@DetailMatchActivity,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionArrayList.add(Manifest.permission.WRITE_CALENDAR)

        }

        if (ContextCompat.checkSelfPermission(
                this@DetailMatchActivity,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionArrayList.add(Manifest.permission.READ_CALENDAR)

        }

        if (permissionArrayList.size > 0) {
            val permissionArray = arrayOfNulls<String>(permissionArrayList.size)

            for (value in permissionArrayList.withIndex()) {
                permissionArray[value.index] = permissionArrayList[value.index]
            }

            ActivityCompat.requestPermissions(
                this@DetailMatchActivity,
                permissionArray,
                MY_PERMISSIONS_REQUEST_READ_CALENDAR
            )
        } else {
            toast("Permission Granted")
            addToCalendar()
        }

    }

    private fun addToCalendar() {
        val titleEvent = detailMatch[0].strEvent
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val stringTimeEvent = toDatetoString(
            detailMatch[0].dateEvent.toString(),
            detailMatch[0].strTime.toString(),
            "yyyy-MM-dd HH:mm:ss"
        )

        val dateTimeEvent = format.parse(stringTimeEvent)
        Log.e("DATE_TO_ADD", dateTimeEvent.toString())
        //convert to long
        val beginTime = dateTimeEvent.time
        //add end-time
        val endTime = beginTime + 3600000
        // intent
        val intentCalendar = Intent(Intent.ACTION_EDIT)
        intentCalendar.type = "vnd.android.cursor.item/event"
        intentCalendar.putExtra(CalendarContract.Events.TITLE, titleEvent)
        intentCalendar.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
        intentCalendar.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
        startActivity(intentCalendar)
    }

}
