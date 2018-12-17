package com.example.pandu.finalproject.team.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.model.Player
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        player = intent.getParcelableExtra("playerData")
        setPlayerDetail()
    }

    private fun setPlayerDetail() {
        tv_pp_name.text = player.strPlayer
        tv_pp_position.text = player.strPosition
        Glide.with(applicationContext).load(player.strThumb).into(iv_pp)
        tv_pp_height.text = player.strHeight
        tv_pp_weight.text = player.strWeight
        tv_pp_desc.text = player.strDescriptionEN
    }
}
