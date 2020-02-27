package main

import android.app.Application
import models.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    //val games = ArrayList<GameModel>()
    lateinit var games: GameStore
    //val players = ArrayList<PlayerModel>()
    lateinit var players: PlayerStore


    override fun onCreate() {
        super.onCreate()
        //games = GameMemStore()
        games = GameJSONStore(applicationContext)
        //players = PlayerMemStore()
        players = PlayerJSONStore(applicationContext)
        info("Game started")
    }
}