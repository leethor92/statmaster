package org.wit.placemark.activities

import android.view.View
import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread
import org.wit.statmaster.activities.GameView
import views.BasePresenter
import views.BaseView
import views.player.PlayerView

class GamePresenter(view: BaseView) : BasePresenter(view) {

    var game = GameModel()

    var edit = false;

    init {
        app = view.application as MainApp

        if (view.intent.hasExtra("game_edit")) {
            edit = true
            game = view.intent.extras?.getParcelable<GameModel>("game_edit")!!
            view.showGame(game)
        }
    }

    fun getPlayers() = app.players.findAll()

    fun doAddPlayer() {
        view?.startActivityForResult<PlayerView>(0)
    }

    fun doEditPlayer(player: PlayerModel) {
        view?.startActivityForResult(view?.intentFor<PlayerView>()?.putExtra("player_edit", player), 0)
    }

    fun doAddOrSave(gameTitle: String, score: String) {
        game.title = gameTitle
        game.score = score
        doAsync {
            if (edit) {
                app.games.update(game)
            } else {
                app.games.create(game)
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
}