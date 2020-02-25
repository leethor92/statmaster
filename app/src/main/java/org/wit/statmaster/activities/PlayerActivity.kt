package org.wit.statmaster.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.activity_player.view.*
import main.MainApp
import models.PlayerModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.statmaster.R

class PlayerActivity : AppCompatActivity(), AnkoLogger {

    var player = PlayerModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        app = application as MainApp

        toolbarPlayer.title = title
        setSupportActionBar(toolbarPlayer)
        info("Player Activity started..")

        btnAdd.setOnClickListener() {
            player.name = playerName.text.toString()

            player.number = number.text.toString()

            if (player.name.isNotEmpty()) {
                app.players.add(player.copy())
                info("add Button Pressed: $player")
                for (i in app.players.indices) {
                    info("Player[$i]:${app.players[i]}")
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancelPlayer -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}