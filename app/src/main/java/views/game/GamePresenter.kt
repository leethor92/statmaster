package org.wit.placemark.activities

import android.util.Log
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

    init {
        app = view.application as MainApp

        doAsync {
            val players = app.players.findAll()

            uiThread {

                if (view.intent.hasExtra("game_edit")) {
                    edit = true
                    game = view.intent.extras?.getParcelable<GameModel>("game_edit")!!

                    var totalPlayerPoints = view.getTotalPlayerPoints(players)
                    var totalPlayerGoals = view.getTotalPlayerGoals(players)

                    view.showGame(game, totalPlayerGoals, totalPlayerPoints)
                }
            }
        }
    }

    fun doAddOrSave(gameTitle: String, score: String, winCheckbox: Boolean, drawCheckbox: Boolean, lossCheckbox: Boolean, spinner: String) {
        game.title = gameTitle
        game.score = score
        game.win = winCheckbox
        game.draw = drawCheckbox
        game.loss = lossCheckbox
        game.goal = "Goals: " + view?.getTotalPlayerGoals( app.players.findAll())
        game.point = "Points: " + view?.getTotalPlayerPoints(app.players.findAll())
        game.players
        game.teamId

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

    fun doEditPlayer(player: PlayerModel, game: GameModel) {
        view?.startActivityForResult(view?.intentFor<PlayerView>()?.putExtra("player_edit", player)?.putExtra("game_data", game), 0)
    }

    fun doCancel() {
        view?.finish()
    }

    fun loadTeams(){
        doAsync {
            val teams = app.teams.findAll()
            uiThread {
                view?.showTeams(teams)
            }
        }
    }

    fun loadPlayers(){
        doAsync {
            val players = game.players
            uiThread {
                view?.showPlayers(players)
            }
        }
    }

    fun doDelete() {
        doAsync {
            app.games.delete(game)
            uiThread {
                view?.finish()
            }
        }
    }

}