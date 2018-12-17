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
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.startActivity


/**
 * A simple [Fragment] subclass.
 *
 */
class LastMatchFragment : Fragment(), MatchView {
    private var listMatches: MutableList<Match> = mutableListOf()
    private var listLeagues: MutableList<League> = mutableListOf()
    private lateinit var lastPresenter: MatchPresenter
    private lateinit var lastAdapter: MatchAdapter
    private lateinit var pbLast: ProgressBar
    private lateinit var srLast: SwipeRefreshLayout
    private lateinit var rvLast: RecyclerView
    private lateinit var spinnerLast : Spinner
    private var spinnerIdDefault = "4328"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srLast = sr_last_match
        pbLast = pb_last_match
        rvLast = rv_last_match
        spinnerLast = spiner_last

        srLast.setOnRefreshListener {
            srLast.isRefreshing = false
            if (isNetworkAvailable(context)){
                signal_status.gone()
                tv_signal_status.gone()
                lastPresenter.getLastMatchList(spinnerIdDefault)
                setSpinnerContent()
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
        lastPresenter = MatchPresenter(this, request, gson)

        if (isNetworkAvailable(context)){
            signal_status.gone()
            tv_signal_status.gone()
            lastPresenter.getLastMatchList(spinnerIdDefault)
            setSpinnerContent()

        }else{
            hideLoading()
            signal_status.visible()
            tv_signal_status.visible()
        }

//        lastPresenter.getLeagues()
    }

    override fun showLoading() {
        pbLast.visible()
    }

    override fun hideLoading() {
        pbLast.gone()
    }

    override fun showMatchList(events: List<Match>) {
        listMatches.clear()
        listMatches.addAll(events)
        rvLast.layoutManager = LinearLayoutManager(context)
        lastAdapter = MatchAdapter(context,listMatches) {
            context?.startActivity<DetailMatchActivity>(
                "matchId" to it.matchId,
                "alarmButton" to "tidak")
        }
        rvLast.adapter = lastAdapter


    }

//    error cause get non-soccer sport, change to static string array
//    override fun showLeagueList(league: List<League>) {
//        listLeagues.clear()
//        listLeagues.addAll(league)
//        setSpinnerContent()
//    }


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
        spinnerLast.adapter = adapter
        spinnerLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val leagues = listLeagues[position]
                val leagueId = leagues.idLeague
                spinnerIdDefault = leagueId.toString()
                lastPresenter.getLastMatchList(spinnerIdDefault)
            }

        }
    }

}
