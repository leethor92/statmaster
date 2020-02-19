package main

import android.app.Application
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val games = ArrayList<GameModel>()
    val players = ArrayList<PlayerModel>()


    override fun onCreate() {
        super.onCreate()
        info("Game started")
        players.add(PlayerModel("Lee", "1"))
        players.add(PlayerModel("Ross", "2"))
        players.add(PlayerModel("David", "3"))

    }
}