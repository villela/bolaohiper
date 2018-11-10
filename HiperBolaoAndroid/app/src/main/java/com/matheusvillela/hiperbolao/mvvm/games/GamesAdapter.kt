package com.matheusvillela.hiperbolao.mvvm.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.model.Game
import com.matheusvillela.hiperbolao.shared.Flag
import kotlinx.android.synthetic.main.item_game.view.*
import org.threeten.bp.format.DateTimeFormatter


class GamesAdapter(private val onGameClickedListener: OnGameClickedListener)
    : RecyclerView.Adapter<GamesAdapter.GamesAdapterHolder>() {

    private var games: List<Game> = mutableListOf()
    private val flagsMap = Flag.values().associateBy({ it.toString() }, { it.drawableRes })

    interface OnGameClickedListener {
        fun onGameClicked(game: Game)
    }

    fun setGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_game, parent, false)
        return GamesAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: GamesAdapterHolder, position: Int) {
        val game = games[position]
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
        holder.game = game
    }

    override fun getItemCount(): Int {
        return games.size
    }

    inner class GamesAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var game: Game? = null

        init {
            itemView.setOnClickListener {
                game?.let { onGameClickedListener.onGameClicked(it) }
            }
        }
    }
}
