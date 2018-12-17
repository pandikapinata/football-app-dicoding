package com.example.pandu.finalproject.match.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.model.Match
import com.example.pandu.finalproject.utils.toDatetoString
import kotlinx.android.synthetic.main.lists_match.view.*

class MatchAdapter(private val ctx: Context?, private val matches: List<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MatchViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(ctx).inflate(R.layout.lists_match, p0, false))
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(p0: MatchViewHolder, p1: Int) {
        p0.bindItem(matches[p1], listener)
    }
}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(match: Match, listener: (Match) -> Unit) {
        itemView.tv_home.text = match.strHomeTeam
        itemView.tv_away.text = match.strAwayTeam
        itemView.tv_score_home.text = match.intHomeScore
        itemView.tv_score_away.text = match.intAwayScore
        // DATE-TIME
        itemView.tv_match_date.text = toDatetoString(match.dateEvent.toString(), match.strTime, "EEE, dd MMM YYYY")
        itemView.tv_match_time.text = toDatetoString(match.dateEvent.toString(), match.strTime, "HH:mm")

        itemView.setOnClickListener {
            listener(match)
        }
    }

}
