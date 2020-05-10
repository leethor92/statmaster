package views.player

import android.content.Intent
import helpers.showImagePicker
import kotlinx.android.synthetic.main.activity_game.*
import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView
import views.IMAGE_REQUEST
import views.game.PlayerAdapter

class PlayerPresenter(view: BaseView) : BasePresenter(view) {

    var player = PlayerModel()
    var game = GameModel()

    var edit = false;

    init {
        if (view.intent.hasExtra("player_edit")) {
            edit = true
            player = view.intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            game = view.intent.extras?.getParcelable<GameModel>("game_data")!!

            player.gameId = game.id

            view.showPlayer(player)
        }
        else
        {
            game = view.intent.extras?.getParcelable<GameModel>("game_data")!!
            player.gameId = game.id
        }

    }

    fun doAddOrSave(name: String, number: String, points: Int, goals: Int, wides: Int, possessions: Int, passes: Int) {
        player.name = name
        player.number = number
        player.point = points
        player.goal = goals
        player.wide = wides
        player.possession = possessions
        player.pass = passes
        player.gameId = game.id

        doAsync {
        if (edit) {
            app.players.update(player)
        } else {
            app.players.create(player)
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
            app.players.delete(player)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doActivityResult(requestCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                player.image = data.data.toString()
                view?.showPlayer(player)
            }
        }
    }
}