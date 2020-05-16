package views.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_game.view.*
import models.GameModel
import models.TeamModel
import org.wit.statmaster.R

interface GameListener {
    fun onGameClick(game: GameModel, team: TeamModel)
}

class GameAdapter constructor(
    private var games: List<GameModel>,
    private val listener: GameListener
) : RecyclerView.Adapter<GameAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_game,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val game = games[holder.adapterPosition]
        val team = TeamModel()
        holder.bind(game, team, listener)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(game: GameModel, team: TeamModel, listener: GameListener) {
            itemView.gameTitle.text = game.title
            itemView.score.text = game.score
            itemView.gameGoals.text = game.goal
            itemView.gamePoints.text = game.point
            itemView.setOnClickListener { listener.onGameClick(game, team) }
        }
    }
}