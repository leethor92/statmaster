package org.wit.statmaster.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.card_player.*
import kotlinx.android.synthetic.main.card_player.view.*
import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.*
import org.wit.statmaster.R

class GameActivity : AppCompatActivity() , AnkoLogger, PlayerListener  {

    var game = GameModel()
    lateinit var app: MainApp
    var player = PlayerModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        app = application as MainApp

        var edit = false

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        loadPlayers()

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Player Activity started..")

        if (intent.hasExtra("game_edit")) {
            edit = true
            game = intent.extras?.getParcelable<GameModel>("game_edit")!!
            gameTitle.setText(game.title)
            score.setText(game.score)
            btnAdd.setText(R.string.save_game)
        }

        btnAdd.setOnClickListener() {
            game.title = gameTitle.text.toString()
            game.score = score.text.toString()

            if (game.title.isEmpty()) {
                toast(R.string.enter_game_title)
            } else {
                if (edit) {
                    app.games.update(game.copy())
                }
                else {
                    app.games.create(game.copy())
                }
                info("add Button Pressed: $game")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_match, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_addPlayer -> startActivityForResult<PlayerActivity>(0)
            R.id.item_delete -> {
                app.games.delete(game)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel) {
        startActivityForResult(intentFor<PlayerActivity>().putExtra("player_edit", player), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPlayers()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadPlayers() {
        showPlayers(app.players.findAll())
    }

    fun showPlayers (players: List<PlayerModel>) {
        recyclerView1.adapter = PlayerAdapter(players, this)
        recyclerView1.adapter?.notifyDataSetChanged()
    }

}
