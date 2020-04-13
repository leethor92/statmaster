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
import kotlinx.android.synthetic.main.activity_game.btnAdd
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.card_player.*
import kotlinx.android.synthetic.main.card_player.view.*
import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.*
import org.wit.placemark.activities.GamePresenter
import org.wit.statmaster.R

class GameView : AppCompatActivity() , AnkoLogger, PlayerListener  {

    var game = GameModel()
    lateinit var presenter: GamePresenter
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        presenter = GamePresenter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        recyclerView1.adapter =
            PlayerAdapter(presenter.getPlayers(), this)
        recyclerView1.adapter?.notifyDataSetChanged()

        btnAdd.setOnClickListener() {
            if (gameTitle.text.toString().isEmpty()) {
                toast(R.string.enter_game_title)
            } else {
                presenter.doAddOrSave(gameTitle.text.toString(), score.text.toString())
            }
        }
    }

    fun showGame(game: GameModel) {
        gameTitle.setText(game.title)
        score.setText(game.score)
        btnAdd.setText(R.string.save_game)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_match, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_addPlayer -> presenter.doAddPlayer()
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel) {
        presenter.doEditPlayer(player)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView1.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showPlayers (players: List<PlayerModel>) {
        recyclerView1.adapter = PlayerAdapter(players, this)
        recyclerView1.adapter?.notifyDataSetChanged()
    }

}
