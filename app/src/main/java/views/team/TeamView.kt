package views.team

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_team.*
import models.GameModel
import models.TeamModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.statmaster.R
import org.wit.statmaster.activities.GameView
import views.BaseView

class TeamView : BaseView() , AnkoLogger, GameListener {

  var team = TeamModel()
  lateinit var presenter: TeamPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_team)

    super.init(teamToolbar, true);

    presenter = initPresenter (TeamPresenter(this)) as TeamPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadGames()

    addGame.setOnClickListener {
      startActivity(intentFor<GameView>().putExtra("team_data", presenter.team))
      finish()
    }
  }

  override fun showTeam(team: TeamModel ) {
    teamName.setText(team.name)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_team, menu)

    val searchView: SearchView = menu?.findItem(R.id.item_search)?.actionView as SearchView
    searchView.queryHint = "Search for a Game"
    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
      override fun onQueryTextChange(newText: String): Boolean {
        presenter.loadGamesSearch(newText)
        return false
      }

      override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isBlank() || query.isEmpty()) presenter.loadGames()
        else presenter.loadGamesSearch(query)
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
      R.id.item_wonGames -> {
        item.isChecked = !item.isChecked
        presenter.doShowWonGames(item.isChecked)
      }
      R.id.item_drawnGames -> {
        item.isChecked = !item.isChecked
        presenter.doShowDrawnGames(item.isChecked)
      }
      R.id.item_lostGames -> {
        item.isChecked = !item.isChecked
        presenter.doShowLostGames(item.isChecked)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onGameClick(game: GameModel, team: TeamModel) {
    presenter.doEditGame(game, presenter.team)
  }

  override fun showGames (games: List<GameModel>) {
    recyclerView.adapter = GameAdapter(games.filter { it.teamId == presenter.team.id }, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadGames()
    super.onActivityResult(requestCode, resultCode, data)
  }
}