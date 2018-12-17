package com.example.pandu.finalproject.team.detail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.model.Team
import kotlinx.android.synthetic.main.fragment_overview_team.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class OverviewTeamFragment : Fragment() {

    private lateinit var teamOverview : Team
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        teamOverview = bundle?.getParcelable("teamExtra")!!

        tv_overview_team.text = teamOverview.strDescriptionEN
    }

}
