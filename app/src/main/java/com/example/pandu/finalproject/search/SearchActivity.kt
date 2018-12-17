package com.example.pandu.finalproject.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.match.adapter.MatchAdapter
import com.example.pandu.finalproject.match.detail.DetailMatchActivity
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.utils.gone
import com.example.pandu.finalproject.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity

class SearchActivity : AppCompatActivity(), SearchView {
    private var listMatchSearch: MutableList<Match> = mutableListOf()
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var searchAdapter: MatchAdapter
    private lateinit var pbSearch: ProgressBar
    private lateinit var rvSearchMatch: RecyclerView
    private var menuItem : Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val textQuery = intent.getStringExtra("text")
        Log.e("EXTRA_QUERY", textQuery)
        pbSearch = pb_search_match
        rvSearchMatch = rv_search_match
        val request = APIRepository()
        val gson = Gson()
        searchPresenter = SearchPresenter(this, request, gson)
        searchPresenter.getMatchBySearch(textQuery)
    }

    override fun showLoading() {
        pbSearch.visible()
    }

    override fun hideLoading() {
        pbSearch.gone()
    }

    override fun showSearchMatch(matches: List<Match>?) {
        Log.e("DATA_SEARCH", matches.toString())
        val matchFilter: List<Match>? = matches?.filter { s -> s.strSport == "Soccer" }
        Log.e("DATA_SEARCH_FILTER", matchFilter.toString())
        listMatchSearch.clear()
        matchFilter?.let { listMatchSearch.addAll(it) }
//        matches?.let { listMatchSearch.addAll(it) }
        rvSearchMatch.layoutManager = LinearLayoutManager(applicationContext)
        searchAdapter = MatchAdapter(applicationContext, listMatchSearch) {
            startActivity<DetailMatchActivity>("matchId" to it.matchId)
        }
        rvSearchMatch.adapter = searchAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            //mencoba cara lain
            R.id.searchButton -> {
                val searchMatch = menuItem?.findItem(R.id.searchButton)?.actionView as android.support.v7.widget.SearchView?
                searchMatch?.queryHint = "Enter team name here"

                searchMatch?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        searchPresenter.getMatchBySearch(p0)
                        return false
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuItem = menu
        return true
    }


}
