package main

import android.app.Application
import models.GameMemStore
import models.GameModel
import models.PlayerMemStore
import models.PlayerModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    //val games = ArrayList<GameModel>()
    val games = GameMemStore()
    //val players = ArrayList<PlayerModel>()
    val players = PlayerMemStore()


    override fun onCreate() {
        super.onCreate()
        info("Game started")
        players.create(PlayerModel(1, "Lee", "1"))
        players.create(PlayerModel(2,"Ross", "2"))
        players.create(PlayerModel(3,"David", "3"))

        games.create(GameModel(1,"Match 1", "1:15 - 2:03"))
        games.create(GameModel(2,"Match 2", "2:25 - 2:03"))
        games.create(GameModel(3,"Match 3", "2:23 - 2:03"))
    }
}