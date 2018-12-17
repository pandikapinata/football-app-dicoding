package com.example.pandu.finalproject.match

import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.api.APIService
import com.example.pandu.finalproject.model.LeagueResponse
import com.example.pandu.finalproject.model.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(
    private val view: MatchView,
    private val apiRepo: APIRepository,
    private val gson: Gson
) {

    fun getLastMatchList(leagueId : String?){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepo.doRequest(
                APIService.getLastMatch(leagueId)).await(), MatchResponse::class.java)
            view.hideLoading()
            data.events?.let { view.showMatchList(it) }
        }
    }

    fun getNextMatchList(leagueId : String?){
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepo.doRequest(
                APIService.getNextMatch(leagueId)).await(), MatchResponse::class.java)
            view.hideLoading()
            data.events?.let { view.showMatchList(it) }
        }
    }

//    fun getLeagues(){
//        view.showLoading()
//
//        GlobalScope.launch(Dispatchers.Main){
//            val data = gson.fromJson(apiRepo.doRequest(
//                APIService.getLeague()).await(), LeagueResponse::class.java)
//            view.hideLoading()
//            data.leagues?.let { view.showLeagueList(it) }
//        }
//    }
//    error cause get non-soccer sport, change to static string array
}