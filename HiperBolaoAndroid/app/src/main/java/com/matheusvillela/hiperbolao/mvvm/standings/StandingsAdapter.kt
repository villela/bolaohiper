package com.matheusvillela.hiperbolao.mvvm.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.Player
import com.matheusvillela.hiperbolao.model.RoundResult
import com.matheusvillela.hiperbolao.model.Standing
import kotlinx.android.synthetic.main.item_standing.view.*

class StandingsAdapter(private val onPlayerClickedListener: OnPlayerClickedListener)
    : RecyclerView.Adapter<StandingsAdapter.StandingsAdapterHolder>() {

    private var standings: List<Standing> = mutableListOf()

    interface OnPlayerClickedListener {
        fun onPlayerClicked(player: Player)
        fun onRoundResultClicked(roundResult: RoundResult, player: Player)
    }

    fun setStandings(standings: List<Standing>) {
        this.standings = standings
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_standing, parent, false)
        return StandingsAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: StandingsAdapterHolder, position: Int) {
        val standing = standings[position]
        holder.standing = standing
        holder.itemView.item_standing_name.text = standing.player.name
        holder.itemView.item_standing_points.text = standing.lastRoundResult.points.toString()
        holder.itemView.item_standing_position.text = standing.position.toString()
        holder.itemView.item_standing_round_arrow_down.visibility =
                if (standing.positionsEarned < 0) View.VISIBLE else View.GONE
        holder.itemView.item_standing_round_arrow_up.visibility =
                if (standing.positionsEarned > 0) View.VISIBLE else View.GONE
        holder.itemView.item_standing_round_positions_earned.text =
                (if (standing.positionsEarned < 0) -standing.positionsEarned else standing.positionsEarned).toString()
        holder.itemView.item_standing_earned_container.visibility =
                if (standing.positionsEarned == 0) View.INVISIBLE else View.VISIBLE
        val pointsEarned = standing.lastRoundResult.bets.sumBy { it.points }
        holder.itemView.item_standing_points_earned.text =
                if (pointsEarned == 0) "0" else "+$pointsEarned"
    }

    override fun getItemCount(): Int {
        return standings.size
    }

    inner class StandingsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var standing: Standing? = null

        init {
            itemView.setOnClickListener {
                standing?.player?.let { onPlayerClickedListener.onPlayerClicked(it) }
            }
            itemView.item_standing_points_earned.setOnClickListener {
                standing?.lastRoundResult?.let { roundResult ->
                    standing?.player?.let { player ->
                        onPlayerClickedListener.onRoundResultClicked(roundResult, player)
                    }
                }
            }
        }
    }
}
