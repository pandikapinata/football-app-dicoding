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
import com.example.pandu.finalproject.match.adapter.MatchAdapter
import com.example.pandu.finalproject.match.detail.DetailMatchActivity
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.utils.gone
import com.example.pandu.finalproject.utils.visible
import kotlinx.android.synthetic.main.fragment_match_fav.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchFavFragment : Fragment() {
    private var listMatchesFav: MutableList<Match> = mutableListOf()
    private lateinit var matchFavAdapter: MatchAdapter
    private lateinit var pbFav: ProgressBar
    private lateinit var srFav: SwipeRefreshLayout
    private lateinit var rvFav: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        srFav = sr_match_fav
        pbFav = pb_match_fav
        rvFav = rv_match_fav
        srFav.setOnRefreshListener {
            srFav.isRefreshing = false
            getMatchLocal()
        }
        getMatchLocal()
    }

    private fun getMatchLocal() {
        pbFav.visible()
        context?.db?.use {
            val selectData = select(Match.TABLE_MATCH)
            val favorites = selectData.parseList(classParser<Match>())
            listMatchesFav.clear()
            listMatchesFav.addAll(favorites)
            rvFav.layoutManager = LinearLayoutManager(context)
            matchFavAdapter = MatchAdapter(context,listMatchesFav) {
                context?.startActivity<DetailMatchActivity>("matchId" to it.matchId)
            }
            rvFav.adapter = matchFavAdapter
        }
        pbFav.gone()
    }

    override fun onResume() {
        super.onResume()
        getMatchLocal()
        matchFavAdapter.notifyDataSetChanged()
    }


}
