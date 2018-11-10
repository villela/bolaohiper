package com.matheusvillela.hiperbolao.mvvm.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.BetWithPoints
import com.matheusvillela.hiperbolao.shared.Flag
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.android.synthetic.main.item_player_bet.view.*
import org.threeten.bp.format.DateTimeFormatter

class RoundResultAdapter : RecyclerView.Adapter<RoundResultAdapter.RoundResultAdapterHolder>() {

    private var bets: List<BetWithPoints> = mutableListOf()
    private val flagsMap = Flag.values().associateBy({ it.toString() }, { it.drawableRes })

    fun setBets(bets: List<BetWithPoints>) {
        this.bets = bets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundResultAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_player_bet_with_result, parent, false)
        return RoundResultAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: RoundResultAdapterHolder, position: Int) {
        val betWithPoints = bets[position]
        val game = betWithPoints.bet.game
        val bet = betWithPoints.bet
        val dtf = DateTimeFormatter.ofPattern("H:mm EE, dd MMM")
        holder.itemView.item_game_when.text = dtf.format(game.start)
        holder.itemView.item_game_awayscore.text = game.score?.awayScore?.toString() ?: ""
        holder.itemView.item_game_homescore.text = game.score?.homeScore?.toString() ?: ""
        holder.itemView.item_game_hometeam.text = game.homeTeam.name
        holder.itemView.item_game_awayteam.text = game.awayTeam.name
        holder.itemView.item_game_in_progress.visibility =
                if (game.score?.finished == false) View.VISIBLE else View.INVISIBLE
        flagsMap[game.homeTeam.id]?.let { holder.itemView.item_game_homeshield.setImageResource(it) }
        flagsMap[game.awayTeam.id]?.let { holder.itemView.item_game_awayshield.setImageResource(it) }

        holder.itemView.item_player_bet_name.text = ""
        holder.itemView.item_player_bet_away_score.text = bet.awayScore.toString()
        holder.itemView.item_player_bet_home_score.text = bet.homeScore.toString()
        val pointsDesc = when {
            betWithPoints.points == 1 -> "${betWithPoints.points} ponto"
            betWithPoints.points == 0 -> "nenhum ponto"
            else -> "${betWithPoints.points} pontos"
        }
        holder.itemView.item_player_bet_points_description.text = pointsDesc
    }

    override fun getItemCount(): Int {
        return bets.size
    }

    inner class RoundResultAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
