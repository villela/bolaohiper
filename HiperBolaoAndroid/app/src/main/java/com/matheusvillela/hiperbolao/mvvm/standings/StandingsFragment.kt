package com.matheusvillela.hiperbolao.mvvm.standings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.Player
import com.matheusvillela.hiperbolao.model.RoundResult
import com.matheusvillela.hiperbolao.mvvm.game.GameActivity
import com.matheusvillela.hiperbolao.mvvm.player.PlayerActivity
import com.matheusvillela.hiperbolao.mvvm.standings.StandingsAdapter.OnPlayerClickedListener
import com.matheusvillela.hiperbolao.shared.ViewStatus
import com.matheusvillela.hiperbolao.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_standings.*
import timber.log.Timber

class StandingsFragment : Fragment(), OnPlayerClickedListener {

    private lateinit var viewModel: StandingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = obtainViewModel(StandingsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_standings_recycler.setHasFixedSize(true)
        fragment_standings_recycler.layoutManager = LinearLayoutManager(activity)
        fragment_standings_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val standingsAdapter = StandingsAdapter(this)
        fragment_standings_recycler.adapter = standingsAdapter
        viewModel.viewStatus.observe(this, Observer {
            if (it != null) {
                fragment_standings_error.visibility = View.GONE
                fragment_standings_loading.visibility = View.GONE
                fragment_standings_swipe.visibility = View.GONE
                val visibleView = when (it) {
                    ViewStatus.ERROR -> fragment_standings_error
                    ViewStatus.LOADING -> fragment_standings_loading
                    ViewStatus.READY -> fragment_standings_swipe
                }
                visibleView.visibility = View.VISIBLE
                fragment_standings_swipe.isRefreshing = it == ViewStatus.LOADING
            }
        })
        viewModel.standings.observe(this, Observer {
            it?.let { standingsAdapter.setStandings(it) }
        })
        fragment_standings_swipe.setOnRefreshListener {
            viewModel.loadStandings()
        }
        fragment_standings_error.setOnClickListener {
            viewModel.loadStandings()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadStandings()
    }

    override fun onPlayerClicked(player: Player) {
        val requireContext = requireContext()
        val myIntent = Intent(requireContext, PlayerActivity::class.java)
        myIntent.putExtra("id", player.id)
        myIntent.putExtra("name", player.name)
        requireContext.startActivity(myIntent)
    }

    override fun onRoundResultClicked(roundResult: RoundResult, player: Player) {
        viewModel.loadRoundResult(roundResult, player)
        RoundResultDialogFragment().show(requireFragmentManager().beginTransaction(), "dialog")
    }
}