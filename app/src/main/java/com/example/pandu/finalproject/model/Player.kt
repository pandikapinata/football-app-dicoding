package com.example.pandu.finalproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val idPlayer: String? = null,
    val idTeam: String? = null,
    val dateBorn: String? = null,
    val strThumb: String? = null,
    val strBirthLocation: String? = null,
    val strDescriptionEN: String? = null,
    val strCutout: String? = null,
    val strFanart1: String? = null,
    val strFanart2: String? = null,
    val strFanart3: String? = null,
    val strFanart4: String? = null,
    val strGender: String? = null,
    val strHeight: String? = null,
    val strNationality: String? = null,
    val strPlayer: String? = null,
    val strPosition: String? = null,
    val strSport: String? = null,
    val strTeam: String? = null,
    val strWeight: String? = null
):Parcelable