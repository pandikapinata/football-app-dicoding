package com.example.pandu.finalproject.search

import com.example.pandu.finalproject.model.Match

interface SearchView {
    fun showLoading()
    fun hideLoading()
    fun showSearchMatch(matches: List<Match>?)
}