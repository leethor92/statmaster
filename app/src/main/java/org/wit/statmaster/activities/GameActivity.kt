package org.wit.statmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*
import main.MainApp
import models.GameModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.statmaster.R

class GameActivity : AppCompatActivity() , AnkoLogger {

    var game = GameModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        app = application as MainApp

        btnAdd.setOnClickListener() {
            game.title = gameTitle.text.toString()
            game.score = score.text.toString()
            if (game.title.isNotEmpty()) {
                app.games.add(game.copy())
                info("add Button Pressed: $game")
                for (i in app.games.indices) {
                    info("Game[$i]:${this.app.games[i]}")
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}