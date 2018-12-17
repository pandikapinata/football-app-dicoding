package com.example.pandu.finalproject.team

import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.api.APIService
import com.example.pandu.finalproject.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(
    private val view: TeamView,
    private val apiRepo: APIRepository,
    private val gson: Gson
)  {
    fun getTeamsByLeague(leagueId : String?){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepo.doRequest(
                APIService.getTeamsByLeague(leagueId)).await(), TeamResponse::class.java)
            view.hideLoading()
            data?.teams.let { view.showTeamList(it) }
        }
    }

    fun getTeamBySearch(text : String?){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepo.doRequest(
                APIService.searchTeam(text)).await(), TeamResponse::class.java)
            view.hideLoading()
            data?.teams.let { view.showTeamList(it) }
        }
    }


}