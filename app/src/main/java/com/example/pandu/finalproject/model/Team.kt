package com.example.pandu.finalproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Team(
    val id: Long?,
    val idTeam: String? = null,
    val strTeam: String? = null,
    val intFormedYear: String? = null,
    val strStadium: String? = null,
    val strDescriptionEN: String? = null,
    var strTeamBadge: String? = null,
    var strSport: String? = null
):Parcelable
{
    companion object {
        const val TABLE_TEAMS: String = "TABLE_TEAMS"
        const val ID: String = "ID_"
        const val ID_TEAM: String = "ID_TEAM"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_FORMED: String = "TEAM_FORMED"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
        const val TEAM_DESC: String = "TEAM_DESC"
        const val TEAM_LOGO: String = "TEAM_LOGO"
        const val MATCH_TYPE: String = "MATCH_TYPE"
    }
}