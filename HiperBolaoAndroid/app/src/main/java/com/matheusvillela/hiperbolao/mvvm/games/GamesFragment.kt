package com.matheusvillela.hiperbolao.mvvm.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.Game
import com.matheusvillela.hiperbolao.shared.ViewStatus
import com.matheusvillela.hiperbolao.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_games.*
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import android.content.Intent
import com.matheusvillela.hiperbolao.mvvm.game.GameActivity


class GamesFragment : Fragment(), GamesAdapter.OnGameClickedListener {

    private lateinit var viewModel: GamesViewModel
    private var setPosition = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = obtainViewModel(GamesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_games_recycler.setHasFixedSize(true)
        fragment_games_recycler.layoutManager = LinearLayoutManager(activity)
        fragment_games_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val gamesAdapter = GamesAdapter(this)
        fragment_games_recycler.adapter = gamesAdapter
        viewModel.viewStatus.observe(this, Observer {
            if (it != null) {
                fragment_games_error.visibility = View.GONE
                fragment_games_loading.visibility = View.GONE
                fragment_games_swipe.visibility = View.GONE
                val visibleView = when (it) {
                    ViewStatus.ERROR -> fragment_games_error
                    ViewStatus.LOADING -> fragment_games_loading
                    ViewStatus.READY -> fragment_games_swipe
                }
                visibleView.visibility = View.VISIBLE
                fragment_games_swipe.isRefreshing = it == ViewStatus.LOADING
            }
        })
        viewModel.games.observe(this, Observer {
            it?.let {
                gamesAdapter.setGames(it)
                val lastGame = it.find { it.start.isAfter(LocalDateTime.now()) }
                fragment_games_recycler.scrollToPosition(it.indexOf(lastGame) - 2)
            }
        })
        fragment_games_swipe.setOnRefreshListener { loadGames() }
        fragment_games_error.setOnClickListener { loadGames() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGames()
    }

    private fun loadGames() {
        viewModel.loadGames()
        setPosition = true
    }

    override fun onGameClicked(game: Game) {
        Timber.d("game clicked: $game")
        val requireContext = requireContext()
        val myIntent = Intent(requireContext, GameActivity::class.java)
        myIntent.putExtra("id", game.id) //Optional parameters
        requireContext.startActivity(myIntent)
    }
}