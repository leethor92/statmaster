package views.gamelist

import com.google.firebase.auth.FirebaseAuth
import models.GameModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread
import org.wit.statmaster.activities.GameView
import views.BasePresenter
import views.BaseView
import views.VIEW
import views.settings.SettingsView

class GameListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddGame() {
    view?.startActivityForResult<GameView>(0)
  }

  fun doSettings() {
    view?.startActivityForResult<SettingsView>(0)
  }

  fun doEditGame(game: GameModel) {
    view?.startActivityForResult(view?.intentFor<GameView>()?.putExtra("game_edit", game), 0)
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

  fun loadGamesSearch(containingString: String){
    view?.showGames(app.games.findAll().filter { it.title.toLowerCase().contains(containingString.toLowerCase()) })
  }

  fun loadGames(){
    doAsync {
      val games = app.games.findAll()
      uiThread {
        view?.showGames(games)
      }
    }
  }

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.games.clear()
    app.players.clear()
    view?.navigateTo(VIEW.LOGIN)
  }
}