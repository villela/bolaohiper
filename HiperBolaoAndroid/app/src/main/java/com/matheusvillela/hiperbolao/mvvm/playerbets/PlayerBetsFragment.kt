package com.matheusvillela.hiperbolao.mvvm.playerbets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.BetWithPoints
import com.matheusvillela.hiperbolao.model.Player
import com.matheusvillela.hiperbolao.model.RoundResult
import com.matheusvillela.hiperbolao.mvvm.standings.RoundResultAdapter
import com.matheusvillela.hiperbolao.mvvm.standings.RoundResultDialogFragment
import com.matheusvillela.hiperbolao.mvvm.standings.StandingsAdapter
import com.matheusvillela.hiperbolao.mvvm.standings.StandingsAdapter.OnPlayerClickedListener
import com.matheusvillela.hiperbolao.mvvm.standings.StandingsViewModel
import com.matheusvillela.hiperbolao.shared.ViewStatus
import com.matheusvillela.hiperbolao.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.fragment_player_bets.*
import timber.log.Timber

class PlayerBetsFragment : Fragment() {

    private lateinit var viewModel: PlayerBetsViewModel

    private var playerId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = obtainViewModel(PlayerBetsViewModel::class.java)
        playerId = this.arguments?.getInt("id") ?: throw RuntimeException()
        return inflater.inflate(R.layout.fragment_player_bets, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_player_bets_recycler.setHasFixedSize(true)
        fragment_player_bets_recycler.layoutManager = LinearLayoutManager(activity)
        fragment_player_bets_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val roundResultAdapter = RoundResultAdapter()
        fragment_player_bets_recycler.adapter = roundResultAdapter
        viewModel.viewStatus.observe(this, Observer {
            if (it != null) {
                fragment_player_bets_error.visibility = View.GONE
                fragment_player_bets_loading.visibility = View.GONE
                fragment_player_bets_recycler.visibility = View.GONE
                val visibleView = when (it) {
                    ViewStatus.ERROR -> fragment_player_bets_error
                    ViewStatus.LOADING -> fragment_player_bets_loading
                    ViewStatus.READY -> fragment_player_bets_recycler
                }
                visibleView.visibility = View.VISIBLE
            }
        })
        viewModel.playerBetsInfo.observe(this, Observer {
            it?.let {
                val list = mutableListOf<BetWithPoints>()
                val map = it.betsWithPoints.associateBy({ it.bet.game.id }, { it.points })
                it.bets.forEach {
                    list.add(BetWithPoints(it, map[it.game.id] ?: 0))
                }
                roundResultAdapter.setBets(list)
            }
        })
        fragment_player_bets_error.setOnClickListener {
            viewModel.loadPlayerBetsInfo(playerId)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPlayerBetsInfo(playerId)
    }
}