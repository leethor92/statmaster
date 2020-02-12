package org.wit.statmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*
import models.GameModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.statmaster.R

class GameActivity : AppCompatActivity() , AnkoLogger {

    var game = GameModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnAdd.setOnClickListener() {
            game.title = gameTitle.text.toString()
            if (game.title.isNotEmpty()) {
                info("add Button Pressed: $game")
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}
