package com.example.pandu.finalproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.model.Team
import org.jetbrains.anko.db.*

class SQLiteDBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FootballApp.db", null, 1) {
    companion object {
        private var instance: SQLiteDBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SQLiteDBHelper {
            if (instance == null) {
                instance = SQLiteDBHelper(ctx.applicationContext)
            }
            return instance as SQLiteDBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Match.TABLE_MATCH, true,
            Match.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Match.MATCH_ID to TEXT + UNIQUE,
            Match.HOME_ID to TEXT,
            Match.AWAY_ID to TEXT,
            Match.MATCH_DATE to TEXT,
            Match.MATCH_TIME to TEXT,
            Match.HOME_TEAM to TEXT,
            Match.AWAY_TEAM to TEXT,
            Match.HOME_SCORE to TEXT,
            Match.AWAY_SCORE to TEXT,
            Match.HOME_SHOTS to TEXT,
            Match.AWAY_SHOTS to TEXT,
            Match.HOME_GOAL to TEXT,
            Match.AWAY_GOAL to TEXT,
            Match.HOME_RED to TEXT,
            Match.AWAY_RED to TEXT,
            Match.HOME_YELLOW to TEXT,
            Match.AWAY_YELLOW to TEXT,
            Match.HOME_GK to TEXT,
            Match.HOME_DFD to TEXT,
            Match.HOME_MDF to TEXT,
            Match.HOME_FW to TEXT,
            Match.AWAY_GK to TEXT,
            Match.AWAY_DFD to TEXT,
            Match.AWAY_MDF to TEXT,
            Match.AWAY_FW to TEXT,
            Match.MATCH_NAME to TEXT,
            Match.SPORT_TYPE to TEXT
        )

        db?.createTable(
            Team.TABLE_TEAMS, true,
            Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Team.ID_TEAM to TEXT,
            Team.TEAM_NAME to TEXT,
            Team.TEAM_FORMED to TEXT,
            Team.TEAM_STADIUM to TEXT,
            Team.TEAM_DESC to TEXT,
            Team.TEAM_LOGO to TEXT,
            Team.MATCH_TYPE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Match.TABLE_MATCH, true)
        db?.dropTable(Team.TABLE_TEAMS, true)
    }
}

val Context.db: SQLiteDBHelper
    get() = SQLiteDBHelper.getInstance(applicationContext)