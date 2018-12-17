package com.example.pandu.finalproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Match(
    val id: Long?,
    @SerializedName("idEvent")
    val matchId: String? = null,
    val idHomeTeam: String? = null,
    val idAwayTeam: String? = null,
    //date-time
    val dateEvent: String? = null,
    val strTime: String? = null,
    val strHomeTeam: String? = null,
    val strAwayTeam: String? = null,
    val intHomeScore: String? = null,
    val intAwayScore: String? = null,
    val intHomeShots: String? = null,
    val intAwayShots: String? = null,
    val strHomeGoalDetails: String? = null,
    val strAwayGoalDetails: String? = null,
    val strHomeRedCards: String? = null,
    val strAwayRedCards: String? = null,
    val strHomeYellowCards: String? = null,
    val strAwayYellowCards: String? = null,
    //Lineups Home
    val strHomeLineupGoalkeeper: String? = null,
    val strHomeLineupDefense: String? = null,
    val strHomeLineupMidfield: String? = null,
    val strHomeLineupForward: String? = null,
    //Lineups Home
    val strAwayLineupGoalkeeper: String? = null,
    val strAwayLineupDefense: String? = null,
    val strAwayLineupMidfield: String? = null,
    val strAwayLineupForward: String? = null,
    val strEvent: String? = null,
    val strSport: String? = null
) : Parcelable {
    companion object {
        const val TABLE_MATCH: String = "TABLE_MATCH"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val MATCH_DATE: String = "MATCH_DATE"
        const val MATCH_TIME: String = "MATCH_TIME"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_SHOTS: String = "HOME_SHOTS"
        const val AWAY_SHOTS: String = "AWAY_SHOTS"
        const val HOME_GOAL: String = "HOME_GOAL"
        const val AWAY_GOAL: String = "AWAY_GOAL"
        const val HOME_RED: String = "HOME_RED"
        const val AWAY_RED: String = "AWAY_RED"
        const val HOME_YELLOW: String = "HOME_YELLOW"
        const val AWAY_YELLOW: String = "AWAY_YELLOW"
        const val HOME_ID: String = "HOME_ID"
        const val AWAY_ID: String = "AWAY_ID"

        const val HOME_GK: String = "HOME_GK"
        const val HOME_DFD: String = "HOME_DFD"
        const val HOME_MDF: String = "HOME_MDF"
        const val HOME_FW: String = "HOME_FW"

        const val AWAY_GK: String = "AWAY_GK"
        const val AWAY_DFD: String = "AWAY_DFD"
        const val AWAY_MDF: String = "AWAY_MDF"
        const val AWAY_FW: String = "AWAY_FW"

        const val MATCH_NAME: String = "MATCH_NAME"
        const val SPORT_TYPE: String = "SPORT_TYPE"
    }
}