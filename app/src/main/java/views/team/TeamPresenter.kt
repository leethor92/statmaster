package views.team

import main.MainApp
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.wit.statmaster.activities.GameView
import views.BasePresenter
import views.BaseView
import views.player.PlayerView

class TeamPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

  var team = TeamModel()
  var edit = false;

  init {
    app = view.application as MainApp

    doAsync {

      uiThread {

        if (view.intent.hasExtra("team_edit")) {
          edit = true
          team = view.intent.extras?.getParcelable<TeamModel>("team_edit")!!
          view.showTeam(team)
        }
      }
    }
  }

  fun doEditPlayer(player: PlayerModel, team: TeamModel) {
    view?.startActivityForResult(view?.intentFor<PlayerView>()?.putExtra("player_edit", player)?.putExtra("team_data", team), 0)
  }

  fun doAddOrSave(teamName: String) {
    team.name = teamName
    team.players =  app.players.findAll().filter {it.teamId == team.id}

    doAsync {
      if (edit) {
        app.teams.update(team)
      } else {
        team.id = app.teams.create(team)
      }
      uiThread {
        view?.finish()
      }
    }
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    doAsync {
      app.players.findAll().filter {it.teamId == team.id}.forEach {
        app.players.delete(it)
      }
      app.teams.delete(team)
      uiThread {
        view?.finish()
      }
    }
  }

  fun loadPlayers() {
    doAsync {
      val players = app.players.findAll()
      uiThread {
        view?.showPlayers(players)
      }
    }
  }

  fun loadPlayersSearch(containingString: String)
  {
    view?.showPlayers(app.players.findAll().filter { it.name.toLowerCase().contains(containingString.toLowerCase()) })
  }

}