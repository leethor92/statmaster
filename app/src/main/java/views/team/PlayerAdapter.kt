package views.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_player.view.*
import models.GameModel
import models.PlayerModel
import org.wit.statmaster.R


interface PlayerListener {
    fun onPlayerClick(player: PlayerModel, game: GameModel)
}

class PlayerAdapter constructor(
    private var players: List<PlayerModel>,
    private val listener: PlayerListener
) : RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_player,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val player = players[holder.adapterPosition]
        val game = GameModel()
        holder.bind(player, game, listener)
    }

    override fun getItemCount(): Int = players.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(player: PlayerModel, game: GameModel, listener: PlayerListener) {
            itemView.playerName.text = player.name
            itemView.number.text = player.number
            Glide.with(itemView.context).load(player.image).into(itemView.imageIcon);
            itemView.setOnClickListener { listener.onPlayerClick(player, game) }
        }
    }
}