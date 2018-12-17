package com.example.pandu.finalproject.team.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.model.Team
import kotlinx.android.synthetic.main.lists_team.view.*

class TeamAdapter(private val ctx: Context?, private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(ctx).inflate(R.layout.lists_team, p0, false))
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(p0: TeamViewHolder, p1: Int) {
        p0.bindItem(teams[p1], listener)
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
    fun bindItem(team: Team, listener: (Team) -> Unit) {
        itemView.tv_team.text = team.strTeam
        Glide.with(itemView.context).load(team.strTeamBadge).into(itemView.iv_team)

        itemView.setOnClickListener {
            listener(team)
        }
    }
}
