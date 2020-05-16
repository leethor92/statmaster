package main

import android.app.Application
import models.*
import models.firebase.GameFireStore
import models.firebase.PlayerFireStore
import models.firebase.TeamFireStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    //val games = ArrayList<GameModel>()
    lateinit var games: GameStore
    //val players = ArrayList<PlayerModel>()
    lateinit var players: PlayerStore
    lateinit var teams: TeamStore

    override fun onCreate() {
        super.onCreate()
        //games = GameMemStore()
        //games = GameJSONStore(applicationContext)
        //games = GameStoreRoom(applicationContext)
        teams = TeamFireStore(applicationContext)
        games = GameFireStore(applicationContext)

        //players = PlayerMemStore()
        //players = PlayerJSONStore(applicationContext)
        //players = PlayerStoreRoom(applicationContext)
        players = PlayerFireStore(applicationContext)
        info("Game started")
    }
}