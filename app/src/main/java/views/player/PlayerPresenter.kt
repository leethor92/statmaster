package org.wit.statmaster.activities

import android.content.Intent
import helpers.showImagePicker
import main.MainApp
import models.PlayerModel
import views.player.PlayerView

class PlayerPresenter(val view: PlayerView) {

    val IMAGE_REQUEST = 1

    var player = PlayerModel()
    var app: MainApp
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("player_edit")) {
            edit = true
            player = view.intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            view.showPlayer(player)
        }
    }

    fun doAddOrSave(name: String, number: String) {
        player.name = name
        player.number = number
        if (edit) {
            app.players.update(player)
        } else {
            app.players.create(player)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.players.delete(player)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doActivityResult(requestCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                player.image = data.data.toString()
                view.showPlayer(player)
            }
        }
    }
}