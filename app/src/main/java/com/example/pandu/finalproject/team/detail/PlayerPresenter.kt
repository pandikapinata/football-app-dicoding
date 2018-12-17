package com.example.pandu.finalproject.team.detail

import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.api.APIService
import com.example.pandu.finalproject.model.PlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val view: PlayerView,
    private val apiRepo: APIRepository,
    private val gson: Gson
) {
    fun getTeamPlayers(teamId : String?){
//        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepo.doRequest(
                APIService.getTeamPlayers(teamId)).await(), PlayerResponse::class.java)
//            view.hideLoading()
            data.player?.let { view.showPlayerList(it) }
        }
    }
}