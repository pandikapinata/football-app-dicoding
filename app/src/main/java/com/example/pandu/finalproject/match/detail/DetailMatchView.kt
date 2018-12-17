package com.example.pandu.finalproject.match.detail

import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.model.Team

interface DetailMatchView {
    fun showMatchDetail(match: List<Match>)
    fun showHomeLogoTeam(logoTeam: List<Team>?)
    fun showAwayLogoTeam(logoTeam: List<Team>?)
}