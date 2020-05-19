package views.team

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.activity_team.recyclerView1
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.statmaster.R
import org.wit.statmaster.activities.GameView
import views.BaseView
import views.gamelist.GameAdapter
import views.gamelist.GameListener
import views.player.PlayerView

class TeamView : BaseView() , AnkoLogger, PlayerListener {

  var team = TeamModel()
  lateinit var presenter: TeamPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_team)

    super.init(teamToolbar, true);

    presenter = initPresenter (TeamPresenter(this)) as TeamPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView1.layoutManager = layoutManager
    presenter.loadPlayers()

    addPlayer.setOnClickListener {
      startActivity(intentFor<PlayerView>().putExtra("team_data", presenter.team))
      finish()
    }
  }

  override fun showTeam(team: TeamModel ) {
    teamName.setText(team.name)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_team, menu)

    val searchView: SearchView = menu?.findItem(R.id.item_search)?.actionView as SearchView
    searchView.queryHint = "Search for a Player"
    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
      override fun onQueryTextChange(newText: String): Boolean {
        presenter.loadPlayersSearch(newText!!)
        return false
      }

      override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isBlank() || query.isEmpty()) presenter.loadPlayers()
        else presenter.loadPlayersSearch(query)
        return false
      }
    })

    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }
      R.id.item_cancel -> {
        presenter.doCancel()
      }
      R.id.item_save -> {
        if (teamName.text.toString().isEmpty()) {
          toast(R.string.enter_team_title)
        }
        else {
          presenter.doAddOrSave(teamName.text.toString())
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onPlayerClick(player: PlayerModel, game: GameModel) {
    presenter.doEditPlayer(player, presenter.team)
  }

  override fun showPlayers (players: MutableList<PlayerModel>) {
    recyclerView1.adapter = PlayerAdapter(players.filter { it.teamId == presenter.team.id }, this)
    recyclerView1.adapter?.notifyDataSetChanged()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadPlayers()
    super.onActivityResult(requestCode, resultCode, data)
  }
}