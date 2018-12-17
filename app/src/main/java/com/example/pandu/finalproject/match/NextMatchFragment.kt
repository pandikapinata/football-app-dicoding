package com.example.pandu.finalproject.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.match.adapter.MatchAdapter
import com.example.pandu.finalproject.match.detail.DetailMatchActivity
import com.example.pandu.finalproject.model.League
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.utils.gone
import com.example.pandu.finalproject.utils.isNetworkAvailable
import com.example.pandu.finalproject.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.startActivity




/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment(), MatchView {
    private var listMatches: MutableList<Match> = mutableListOf()
    private var listLeagues: MutableList<League> = mutableListOf()
    private lateinit var nextPresenter: MatchPresenter
    private lateinit var nextAdapter: MatchAdapter
    private lateinit var pbNext: ProgressBar
    private lateinit var srNext: SwipeRefreshLayout
    private lateinit var rvNext: RecyclerView
    private lateinit var spinnerNext : Spinner

    private var spinnerIdDefault = "4328"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srNext = sr_next_match
        pbNext = pb_next_match
        rvNext = rv_next_match
        spinnerNext = spinner_next

        srNext.setOnRefreshListener {
            srNext.isRefreshing = false
            if (isNetworkAvailable(context)){
                signal_status.gone()
                tv_signal_status.gone()
                nextPresenter.getNextMatchList(spinnerIdDefault)
                hideLoading()
            }else{
                hideLoading()
                signal_status.visible()
                tv_signal_status.visible()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val request = APIRepository()
        val gson = Gson()
        nextPresenter = MatchPresenter(this, request, gson)

        if (isNetworkAvailable(context)){
            signal_status.gone()
            tv_signal_status.gone()
            nextPresenter.getNextMatchList(spinnerIdDefault)
            setSpinnerContent()

        }else{
            hideLoading()
            signal_status.visible()
            tv_signal_status.visible()
        }

    }

    private fun setSpinnerContent() {
        val arrayLeagues : MutableList<String> = mutableListOf()
        val nameLeague = resources.getStringArray(R.array.league_name)
        val idLeague = resources.getStringArray(R.array.league_id)
        listLeagues.clear()
        for (i in idLeague.indices){
            listLeagues.add(League(idLeague[i],nameLeague[i]))
        }
        for (value in listLeagues){
            arrayLeagues.add(value.strLeague.toString())
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayLeagues)
        spinnerNext.adapter = adapter
        spinnerNext.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val leagues = listLeagues[position]
                val leagueId = leagues.idLeague
                spinnerIdDefault = leagueId.toString()
                nextPresenter.getNextMatchList(spinnerIdDefault)
            }

        }
    }

    override fun showLoading() {
        pbNext.visible()
    }

    override fun hideLoading() {
        pbNext.gone()
    }

    override fun showMatchList(events: List<Match>) {

        listMatches.clear()
        listMatches.addAll(events)
        rvNext.layoutManager = LinearLayoutManager(context)
        nextAdapter = MatchAdapter(context,listMatches) {
            context?.startActivity<DetailMatchActivity>(
                "matchId" to it.matchId,
                "alarmButton" to "ada")
        }
        rvNext.adapter = nextAdapter


    }
}
