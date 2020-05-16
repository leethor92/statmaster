package views.teamlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_team.view.*
import models.TeamModel
import org.wit.statmaster.R

interface TeamListener {
  fun onTeamClick(team: TeamModel)
}

class TeamAdapter constructor(
    private var teams: List<TeamModel>,
    private val listener: TeamListener
) : RecyclerView.Adapter<TeamAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_team,
            parent,
            false
        )
    )
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val team = teams[holder.adapterPosition]
    holder.bind(team, listener)
  }

  override fun getItemCount(): Int = teams.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(team: TeamModel, listener: TeamListener) {
      itemView.teamName.text = team.name
      itemView.setOnClickListener { listener.onTeamClick(team) }
    }
  }
}