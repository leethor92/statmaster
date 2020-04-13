package org.wit.placemark.activities

import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.statmaster.activities.GameView
import views.PlayerView

class GamePresenter(val view: GameView) {

    var game = GameModel()
    var app: MainApp

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
        view.startActivityForResult<PlayerView>(0)
    }

    fun doEditPlayer(player: PlayerModel) {
        view.startActivityForResult(view.intentFor<PlayerView>().putExtra("player_edit", player), 0)
    }

    fun doAddOrSave(gameTitle: String, score: String) {
        game.title = gameTitle
        game.score = score
        if (edit) {
            app.games.update(game)
        } else {
            app.games.create(game)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.games.delete(game)
        view.finish()
    }

    fun doActivityResult() {
            view.showGame(game)
    }
}