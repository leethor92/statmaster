package org.wit.statmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.card_player.view.*
import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.*
import org.wit.statmaster.R

class GameActivity : AppCompatActivity() , AnkoLogger, PlayerListener  {

    var game = GameModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        //recyclerView1.adapter = PlayerAdapter(app.players)
        recyclerView1.adapter = PlayerAdapter(app.players.findAll(), this)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Player Activity started..")

        if (intent.hasExtra("game_edit")) {
            game = intent.extras?.getParcelable<GameModel>("game_edit")!!
            gameTitle.setText(game.title)
            score.setText(game.score)
        }

        btnAdd.setOnClickListener() {
            game.title = gameTitle.text.toString()
            game.score = score.text.toString()
            if (game.title.isNotEmpty()) {
                app.games.create(game.copy())
                info("add Button Pressed: $game")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            } else {
                toast("Please Enter a title")
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
        }
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel) {
        startActivityForResult(intentFor<PlayerActivity>().putExtra("player_edit", player), 0)
    }
}
