package views.player

import android.content.Intent
import helpers.showImagePicker
import main.MainApp
import models.PlayerModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView
import views.IMAGE_REQUEST

class PlayerPresenter(view: BaseView) : BasePresenter(view) {

    var player = PlayerModel()

    var edit = false;

    init {
        if (view.intent.hasExtra("player_edit")) {
            edit = true
            player = view.intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            view.showPlayer(player)
        }
    }

    fun doAddOrSave(name: String, number: String, points: Int, goals: Int, wides: Int, possessions: Int, passess: Int) {
        doAsync {
            player.name = name
            player.number = number
            player.point = points
            player.goal = goals
            player.wide = wides
            player.possession = possessions
            player.pass = passess
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