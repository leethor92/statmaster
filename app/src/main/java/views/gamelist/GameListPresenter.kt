package org.wit.statmaster.activities

import main.MainApp
import models.GameModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class GameListPresenter(val view: GameListView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getGames() = app.games.findAll()

    fun doAddGame() {
        view.startActivityForResult<GameView>(0)
    }

    fun doEditGame(game: GameModel) {
        view.startActivityForResult(view.intentFor<GameView>().putExtra("game_edit", game), 0)
    }

}