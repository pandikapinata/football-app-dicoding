package com.example.pandu.finalproject.team.detail

import com.example.pandu.finalproject.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(players: List<Player>)
}