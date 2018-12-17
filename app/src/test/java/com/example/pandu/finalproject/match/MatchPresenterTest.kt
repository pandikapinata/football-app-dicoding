package com.example.pandu.finalproject.match

import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.api.APIService
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.model.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test


import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: APIRepository

    private lateinit var matchPresenter: MatchPresenter
    private val matches: MutableList<Match> = mutableListOf()
    private val matchResponse = MatchResponse(matches)
    private val leagueId = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchPresenter = MatchPresenter(view, apiRepo, gson)
    }

    @Test
    fun getLastMatchList() {
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepo.doRequest(APIService.getLastMatch(leagueId)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(matchResponse)

            matchPresenter.getLastMatchList(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(matches)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getNextMatchList() {
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepo.doRequest(APIService.getNextMatch(leagueId)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(matchResponse)

            matchPresenter.getNextMatchList(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(matches)
            Mockito.verify(view).hideLoading()
        }
    }
}