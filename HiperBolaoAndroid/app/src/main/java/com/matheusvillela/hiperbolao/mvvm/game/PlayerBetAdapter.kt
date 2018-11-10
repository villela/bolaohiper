package com.matheusvillela.hiperbolao.mvvm.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.PlayerWithBetAndScore
import kotlinx.android.synthetic.main.item_player_bet.view.*


class PlayerBetAdapter : RecyclerView.Adapter<PlayerBetAdapter.GamesAdapterHolder>() {

    private var playersWithBetAndScore: List<PlayerWithBetAndScore> = mutableListOf()

    fun setPlayersWithBets(playersWithBetAndScore: List<PlayerWithBetAndScore>) {
        this.playersWithBetAndScore = playersWithBetAndScore
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_player_bet, parent, false)
        return GamesAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: GamesAdapterHolder, position: Int) {
        val item = playersWithBetAndScore[position]
        holder.itemView.item_player_bet_name.text = item.player.name
        holder.itemView.item_player_bet_away_score.text = item.bet.awayScore.toString()
        holder.itemView.item_player_bet_home_score.text = item.bet.homeScore.toString()
        holder.itemView.item_player_bet_points_description.text = item.score?.let {
            when {
                item.score == 1 -> "$it ponto"
                item.score == 0 -> "nenhum ponto"
                else -> "$it pontos"
            }
        } ?: ""

    }

    override fun getItemCount(): Int {
        return playersWithBetAndScore.size
    }

    inner class GamesAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
