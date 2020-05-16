package views.team

import main.MainApp
import models.GameModel
import models.TeamModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.wit.statmaster.activities.GameView
import views.BasePresenter
import views.BaseView

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

  fun doEditGame(game: GameModel, team: TeamModel) {
    view?.startActivityForResult(view?.intentFor<GameView>()?.putExtra("game_edit", game)?.putExtra("team_data", team), 0)
  }

  fun doShowWonGames(checked: Boolean){
    view?.showGames(if (checked) app.games.findAll().filter { it.win } else app.games.findAll())
  }

  fun doShowDrawnGames(checked: Boolean){
    view?.showGames(if (checked) app.games.findAll().filter { it.draw } else app.games.findAll())
  }

  fun doShowLostGames(checked: Boolean){
    view?.showGames(if (checked) app.games.findAll().filter { it.loss } else app.games.findAll())
  }

  fun doAddOrSave(teamName: String) {
    team.name = teamName

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
      app.games.findAll().filter {it.teamId == team.id}.forEach {
        app.games.delete(it)
      }
      app.teams.delete(team)
      uiThread {
        view?.finish()
      }
    }
  }

  fun loadGames() {
    doAsync {
      val games = app.games.findAll()
      uiThread {
        view?.showGames(games)
      }
    }
  }

  fun loadGamesSearch(containingString: String)
  {
    view?.showGames(app.games.findAll().filter { it.title.toLowerCase().contains(containingString.toLowerCase()) })
  }

}