package com.example.pandu.finalproject.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.favorite.adapter.FavoritePagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*


/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_favorite.adapter = FavoritePagerAdapter(childFragmentManager)
        tl_favorite.setupWithViewPager(vp_favorite)
    }


}
