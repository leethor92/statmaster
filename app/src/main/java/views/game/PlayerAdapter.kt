package views.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.card_player.view.*
import models.GameModel
import models.PlayerModel
import org.wit.statmaster.R
import kotlin.math.round


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
            itemView.totalPoints.text = (player.point.toString() + " points")
            itemView.totalGoals.text = (player.goal.toString() + " goals")
            itemView.totalWides.text = (player.wide.toString() + " wides")
            itemView.total_passes.text = (player.pass.toString() + " passes")
            itemView.total_possessions.text = (player.possession.toString() + " possessions")
            itemView.accuracy.text = (player.accuracy.toString() + "%")
            itemView.setOnClickListener { listener.onPlayerClick(player, game) }

            itemView.add_point.setOnClickListener(View.OnClickListener {
                player.point++
                accuracyCalc(player)

                itemView.accuracy.text = (player.accuracy.toString() + "%")
                itemView.totalPoints.text = (player.point.toString() + " points")
            })
            itemView.minus_point.setOnClickListener(View.OnClickListener {
                if(player.point > 0) {
                    player.point--
                }
                accuracyCalc(player)
                itemView.accuracy.text = (player.accuracy.toString() + "%")
                itemView.totalPoints.text = (player.point.toString() + " points")

            })

            itemView.add_goal.setOnClickListener(View.OnClickListener {
                player.goal++
                accuracyCalc(player)
                itemView.accuracy.text = (player.accuracy.toString() + "%")
                itemView.totalGoals.text = (player.goal.toString() + " goals")
            })
            itemView.minus_goal.setOnClickListener(View.OnClickListener {
                if(player.goal > 0) {
                    player.goal--
                }
                accuracyCalc(player)
                itemView.accuracy.text = (player.accuracy.toString() + "%")
                itemView.totalGoals.text = (player.goal.toString() + " goals")
            })

            itemView.add_wide.setOnClickListener(View.OnClickListener {
                player.wide++
                accuracyCalc(player)
                itemView.accuracy.text = (player.accuracy.toString() + "%")
                itemView.totalWides.text = (player.wide.toString() + " wides")
            })
            itemView.minus_wide.setOnClickListener(View.OnClickListener {
                if(player.wide > 0) {
                    player.wide--
                }
                accuracyCalc(player)
                itemView.accuracy.text = (player.accuracy.toString() + "%")
                itemView.totalWides.text = (player.wide.toString() + " wides")
            })

            itemView.add_possession.setOnClickListener(View.OnClickListener {
                player.possession++
                itemView.total_possessions.text = (player.possession.toString() + " possessions")
            })
            itemView.minus_possession.setOnClickListener(View.OnClickListener {
                if(player.possession > 0) {
                    player.possession--
                }
                itemView.total_possessions.text = (player.possession.toString() + " possessions")
            })

            itemView.add_pass.setOnClickListener(View.OnClickListener {
                player.pass++
                itemView.total_passes.text = (player.pass.toString() + " passes")
            })
            itemView.minus_pass.setOnClickListener(View.OnClickListener {
                if(player.pass > 0) {
                    player.pass--
                }
                itemView.total_passes.text = (player.pass.toString() + " passes")
            })
        }

        private fun accuracyCalc(player: PlayerModel) {

                player.accuracy = round((player.point.toDouble() + player.goal.toDouble()) / (player.goal.toDouble() + player.point.toDouble() + player.wide.toDouble()) * 100.toDouble())
        }

    }
}