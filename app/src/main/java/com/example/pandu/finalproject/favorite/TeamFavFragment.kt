package com.example.pandu.finalproject.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.db.db
import com.example.pandu.finalproject.model.Team
import com.example.pandu.finalproject.team.adapter.TeamAdapter
import com.example.pandu.finalproject.team.detail.TeamDetailActivity
import com.example.pandu.finalproject.utils.gone
import com.example.pandu.finalproject.utils.visible
import kotlinx.android.synthetic.main.fragment_team_fav.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity



/**
 * A simple [Fragment] subclass.
 *
 */
class TeamFavFragment : Fragment() {
    private var teamsFav : MutableList<Team> = mutableListOf()
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var pbTeam: ProgressBar
    private lateinit var srTeam: SwipeRefreshLayout
    private lateinit var rvTeam: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        srTeam = sr_teams_fav
        pbTeam = pb_teams_fav
        rvTeam = rv_teams_fav
        srTeam.setOnRefreshListener {
            srTeam.isRefreshing = false
            getTeamLocal()
        }
        getTeamLocal()
    }

    private fun getTeamLocal() {
        pbTeam.visible()
        context?.db?.use {
            val selectData = select(Team.TABLE_TEAMS)
            val favorites = selectData.parseList(classParser<Team>())
            teamsFav.clear()
            teamsFav.addAll(favorites)
            rvTeam.layoutManager = LinearLayoutManager(context)
            teamAdapter = TeamAdapter(context,teamsFav) {
                context?.startActivity<TeamDetailActivity>("teamExtra" to it)
            }
            rvTeam.adapter = teamAdapter
        }
        pbTeam.gone()
    }

    override fun onResume() {
        super.onResume()
        getTeamLocal()
        teamAdapter.notifyDataSetChanged()
    }


}
