package org.wit.statmaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import helpers.readImage
import helpers.readImageFromPath
import helpers.showImagePicker
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
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        app = application as MainApp

        var edit = false

        toolbarPlayer.title = title
        setSupportActionBar(toolbarPlayer)
        info("Player Activity started..")

        if (intent.hasExtra("player_edit")) {
            edit = true
            player = intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            playerName.setText(player.name)
            number.setText(player.number)
            playerImage.setImageBitmap(readImageFromPath(this, player.image))
            if (player.image != null) {
                chooseImage.setText(R.string.change_player_image)
            }
            btnAdd.setText(R.string.save_player)

        }

        btnAdd.setOnClickListener() {
            player.name = playerName.text.toString()
            player.number = number.text.toString()
            if (player.name.isEmpty()) {
                toast(R.string.enter_player_title)
            } else {
                if (edit) {
                    app.players.update(player.copy())
                } else {
                    app.players.create(player.copy())
                }
                info("add Button Pressed: $player")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
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
            R.id.item_delete -> {
                app.players.delete(player)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    player.image = data.getData().toString()
                    playerImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_player_image)
                }
            }
        }
    }
}