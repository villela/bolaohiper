package com.matheusvillela.hiperbolao.mvvm.game

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.shared.Flag
import com.matheusvillela.hiperbolao.shared.ViewStatus
import com.matheusvillela.hiperbolao.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.item_game.*
import org.threeten.bp.format.DateTimeFormatter

class GameActivity : AppCompatActivity() {

    private var gameId: Int = 0

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameId = intent.extras?.getInt("id") ?: throw RuntimeException()

        activity_game_recycler.setHasFixedSize(true)
        activity_game_recycler.layoutManager = LinearLayoutManager(this)
        activity_game_recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val playerBetAdapter = PlayerBetAdapter()
        activity_game_recycler.adapter = playerBetAdapter

        viewModel = obtainViewModel(GameViewModel::class.java)
        viewModel.loadGame(gameId)

        viewModel.viewStatus.observe(this, Observer {
            if (it != null) {
                activity_game_error.visibility = View.GONE
                activity_game_loading.visibility = View.GONE
                activity_game_linear.visibility = View.GONE
                val visibleView = when (it) {
                    ViewStatus.ERROR -> activity_game_error
                    ViewStatus.LOADING -> activity_game_loading
                    ViewStatus.READY -> activity_game_linear
                }
                visibleView.visibility = View.VISIBLE
            }
        })

        viewModel.game.observe(this, Observer {
            it?.let { item ->
                val dtf = DateTimeFormatter.ofPattern("H:mm EE, dd MMM")
                item_game_when.text = dtf.format(item.game.start)
                item_game_awayscore.text = item.game.score?.awayScore?.toString() ?: ""
                item_game_homescore.text = item.game.score?.homeScore?.toString() ?: ""
                item_game_hometeam.text = item.game.homeTeam.name
                item_game_awayteam.text = item.game.awayTeam.name
                val values = Flag.values()
                values.find { item.game.homeTeam.id == it.toString() }?.drawableRes?.let {
                    item_game_homeshield.setImageResource(it)
                }
                values.find { item.game.awayTeam.id == it.toString() }?.drawableRes?.let {
                    item_game_awayshield.setImageResource(it)
                }
                item_game_in_progress.visibility =
                        if (item.game.score?.finished == false) View.VISIBLE else View.INVISIBLE
                playerBetAdapter.setPlayersWithBets(item.bets.sortedByDescending { it.score })
            }
        })

        activity_game_error.setOnClickListener { viewModel.loadGame(gameId) }
    }
}
