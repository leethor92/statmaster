package org.wit.placemark.activities

import main.MainApp
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.*
import views.BasePresenter
import views.BaseView
import views.player.PlayerView

class GamePresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

    var game = GameModel()
    var edit = false;
    var team = TeamModel()

    init {
        app = view.application as MainApp

        doAsync {
            val players = app.players.findAll()
            uiThread {

                if (view.intent.hasExtra("game_edit")) {
                    edit = true
                    game = view.intent.extras?.getParcelable<GameModel>("game_edit")!!
                    team = view.intent.extras?.getParcelable<TeamModel>("team_data")!!
                    var totalPlayerPoints = view.getTotalPlayerPoints(players)
                    var totalPlayerGoals = view.getTotalPlayerGoals(players)
                    view.showGame(game, totalPlayerGoals, totalPlayerPoints)
                }
                else
                {
                    team = view.intent.extras?.getParcelable<TeamModel>("team_data")!!
                }
            }
        }
    }

    fun doEditPlayer(player: PlayerModel, game: GameModel) {
        view?.startActivityForResult(view?.intentFor<PlayerView>()?.putExtra("player_edit", player)?.putExtra("game_data", game), 0)
    }

    fun doAddOrSave(gameTitle: String, score: String, winCheckbox: Boolean, drawCheckbox: Boolean, lossCheckbox: Boolean) {
        game.title = gameTitle
        game.score = score
        game.win = winCheckbox
        game.draw = drawCheckbox
        game.teamId = team.id
        game.loss = lossCheckbox
        game.goal = "Goals: " + view?.getTotalPlayerGoals( app.players.findAll())
        game.point = "Points: " + view?.getTotalPlayerPoints(app.players.findAll())

        doAsync {
            if (edit) {
                app.games.update(game)
            } else {
                game.id = app.games.create(game)
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
            app.players.findAll().filter {it.gameId == game.id}.forEach {
                app.players.delete(it)
            }
            app.games.delete(game)
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