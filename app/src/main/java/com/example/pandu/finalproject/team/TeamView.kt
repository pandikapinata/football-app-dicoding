package com.example.pandu.finalproject.team

import com.example.pandu.finalproject.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(teams: List<Team>?)
}