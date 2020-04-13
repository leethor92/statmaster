package views.gamelist

import main.MainApp
import models.GameModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.statmaster.activities.GameView
import views.BasePresenter
import views.BaseView

class GameListPresenter(view: BaseView) : BasePresenter(view) {

    fun doAddGame() {
        view?.startActivityForResult<GameView>(0)
    }

    fun doEditGame(game: GameModel) {
        view?.startActivityForResult(view?.intentFor<GameView>()?.putExtra("game_edit", game), 0)
    }

    fun loadGames(){
        view?.showGames(app.games.findAll())
    }
}