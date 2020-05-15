package org.wit.placemark.activities

import main.MainApp
import models.GameModel
import models.PlayerModel
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

    fun doEditPlayer(player: PlayerModel, game: GameModel) {
        view?.startActivityForResult(view?.intentFor<PlayerView>()?.putExtra("player_edit", player)?.putExtra("game_data", game), 0)
    }

    fun doAddOrSave(gameTitle: String, score: String, winCheckbox: Boolean, totalPlayerGoals: String, totalPlayerPoints: String) {
        game.title = gameTitle
        game.score = score
        game.win = winCheckbox
        game.goal = "Goals:" + view?.getTotalPlayerGoals( app.players.findAll())
        game.point = "Points:" + view?.getTotalPlayerPoints(app.players.findAll())

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