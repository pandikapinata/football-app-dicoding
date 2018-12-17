package com.example.pandu.finalproject.match

import com.example.pandu.finalproject.model.League
import com.example.pandu.finalproject.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(events: List<Match>)
//    fun showLeagueList(league: List<League>)
}