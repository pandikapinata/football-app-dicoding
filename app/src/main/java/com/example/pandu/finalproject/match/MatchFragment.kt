package com.example.pandu.finalproject.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*

import com.example.pandu.finalproject.R
import com.example.pandu.finalproject.match.adapter.MatchPagerAdapter
import com.example.pandu.finalproject.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

class MatchFragment : Fragment() {

    private var viewPager:ViewPager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_match.adapter = MatchPagerAdapter(childFragmentManager)
        tl_match.setupWithViewPager(vp_match)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchTeam = menu?.findItem(R.id.searchButton)?.actionView as SearchView?
        searchTeam?.queryHint = "Enter team name here"

        searchTeam?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                context?.startActivity<SearchActivity>("text" to p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

}
