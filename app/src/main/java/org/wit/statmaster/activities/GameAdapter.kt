package org.wit.statmaster.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_game.view.*
import models.GameModel
import org.wit.statmaster.R


class GameAdapter constructor(private var games: List<GameModel>) :
    RecyclerView.Adapter<GameAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_game,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val game = games[holder.adapterPosition]
        holder.bind(game)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(game: GameModel) {
            itemView.gameTitle.text = game.title
            itemView.score.text = game.score
        }
    }
}