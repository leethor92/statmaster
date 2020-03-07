package org.wit.statmaster.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.card_player.view.*
import models.PlayerModel
import org.wit.statmaster.R


interface PlayerListener {
    fun onPlayerClick(player: PlayerModel)
}

class PlayerAdapter constructor(
    private var players: List<PlayerModel>,
    private val listener: PlayerListener
) : RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_player,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val player = players[holder.adapterPosition]
        holder.bind(player, listener)
    }

    override fun getItemCount(): Int = players.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(player: PlayerModel, listener: PlayerListener) {
            itemView.playerName.text = player.name
            itemView.number.text = player.number
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, player.image))
            itemView.totalPoints.text = player.point.toString()
            itemView.totalGoals.text = player.goal.toString()
            itemView.totalWides.text = player.wide.toString()
            itemView.total_passes.text = player.pass.toString()
            itemView.total_possessions.text = player.possession.toString()
            itemView.accuracy.text = player.accuracy.toString()
            itemView.setOnClickListener { listener.onPlayerClick(player) }
        }
    }
}