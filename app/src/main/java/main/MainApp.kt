package main

import android.app.Application
import models.GameModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val games = ArrayList<GameModel>()

    override fun onCreate() {
        super.onCreate()
        info("Game started")
    }
}