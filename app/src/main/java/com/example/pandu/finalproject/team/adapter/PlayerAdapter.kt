package com.example.pandu.finalproject.team.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.model.Player
import kotlinx.android.synthetic.main.lists_player.view.*

class PlayerAdapter(private val ctx: Context?, private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(ctx).inflate(R.layout.lists_player, p0, false))
    }

    override fun getItemCount(): Int {
        return player.size
    }

    override fun onBindViewHolder(p0: PlayerViewHolder, p1: Int) {
        p0.bindItem(player[p1], listener)
    }
}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(player: Player, listener: (Player) -> Unit) {
        itemView.tv_player.text = player.strPlayer
        itemView.tv_player_position.text = player.strPosition
        Glide.with(itemView.context).load(player.strCutout).into(itemView.iv_player)

        itemView.setOnClickListener {
            listener(player)
        }
    }

}
