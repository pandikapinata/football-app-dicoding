package com.example.pandu.finalproject.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.model.League
import com.example.pandu.finalproject.model.Team
import com.example.pandu.finalproject.team.adapter.TeamAdapter
import com.example.pandu.finalproject.team.detail.TeamDetailActivity
import com.example.pandu.finalproject.utils.gone
import com.example.pandu.finalproject.utils.isNetworkAvailable
import com.example.pandu.finalproject.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamFragment : Fragment(), TeamView {
    private var listTeams: MutableList<Team> = mutableListOf()
    private var listLeagues: MutableList<League> = mutableListOf()
    private lateinit var teamPresenter: TeamPresenter
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var pbTeam: ProgressBar
    private lateinit var srTeam: SwipeRefreshLayout
    private lateinit var rvTeam: RecyclerView
    private lateinit var spinnerTeam : Spinner
    private var spinnerIdDefault = "4328"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srTeam = sr_teams
        pbTeam = pb_teams
        rvTeam = rv_teams
        spinnerTeam = spinner_teams

        srTeam.setOnRefreshListener {
            srTeam.isRefreshing = false
            if (isNetworkAvailable(context)){
                signal_status.gone()
                tv_signal_status.gone()
                teamPresenter.getTeamsByLeague(spinnerIdDefault)
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
        teamPresenter = TeamPresenter(this, request, gson)
        if (isNetworkAvailable(context)){
            setHasOptionsMenu(true)
            signal_status.gone()
            tv_signal_status.gone()
            teamPresenter.getTeamsByLeague(spinnerIdDefault)
            setSpinnerContent()
            hideLoading()
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
        spinnerTeam.adapter = adapter
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val leagues = listLeagues[position]
                val leagueId = leagues.idLeague
                spinnerIdDefault = leagueId.toString()
                teamPresenter.getTeamsByLeague(spinnerIdDefault)
            }

        }
    }

    override fun showLoading() {
        pbTeam.visible()
    }

    override fun hideLoading() {
        pbTeam.gone()
    }

    override fun showTeamList(teams: List<Team>?) {
        val teamFilter: List<Team>? = teams?.filter { s -> s.strSport == "Soccer" }
        listTeams.clear()
        teamFilter?.let { listTeams.addAll(it) }
        rvTeam.layoutManager = LinearLayoutManager(context)
        teamAdapter = TeamAdapter(context,listTeams) {
            context?.startActivity<TeamDetailActivity>("teamExtra" to it)
        }
        rvTeam.adapter = teamAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchTeam = menu?.findItem(R.id.searchButton)?.actionView as SearchView?
        searchTeam?.queryHint = "Enter team name here"

        searchTeam?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                teamPresenter.getTeamBySearch(p0)
                return false
            }
        })

        searchTeam?.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
//                setSpinnerContent()
                return true
            }
        })
    }



}
