package com.example.pandu.finalproject.search

import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.api.APIService
import com.example.pandu.finalproject.model.MatchResponse
import com.example.pandu.finalproject.model.SearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(
    private val view: SearchView,
    private val apiRepo: APIRepository,
    private val gson: Gson
) {
    fun getMatchBySearch(text : String?){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepo.doRequest(
                APIService.searchMatch(text)).await(), SearchResponse::class.java)
            view.hideLoading()
            data?.event.let { view.showSearchMatch(it) }
        }
    }
}