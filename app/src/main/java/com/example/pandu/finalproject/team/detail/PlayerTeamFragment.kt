package com.example.pandu.finalproject.team.detail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.api.APIRepository
import com.example.pandu.finalproject.model.Player
import com.example.pandu.finalproject.model.Team
import com.example.pandu.finalproject.team.adapter.PlayerAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player_team.*
import org.jetbrains.anko.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayerTeamFragment : Fragment(), PlayerView {
    private var listPlayer: MutableList<Player> = mutableListOf()
    private lateinit var playerPresenter: PlayerPresenter
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var rvPlayer: RecyclerView
    private lateinit var teamPlayer : Team

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        teamPlayer = bundle?.getParcelable("teamExtra")!!
        val request = APIRepository()
        val gson = Gson()
        rvPlayer = rv_players
        playerPresenter = PlayerPresenter(this, request, gson)
        playerPresenter.getTeamPlayers(teamPlayer.idTeam)

    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPlayerList(players: List<Player>) {
        listPlayer.clear()
        listPlayer.addAll(players)
        rvPlayer.layoutManager = LinearLayoutManager(context)
        playerAdapter = PlayerAdapter(context,listPlayer) {
            context?.startActivity<PlayerDetailActivity>("playerData" to it)
        }
        rvPlayer.adapter = playerAdapter
    }



}
