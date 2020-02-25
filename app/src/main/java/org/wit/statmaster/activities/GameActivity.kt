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
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.statmaster.R

class GameActivity : AppCompatActivity() , AnkoLogger {

    var game = GameModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        recyclerView1.adapter = PlayerAdapter(app.players)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Player Activity started..")

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

    class PlayerAdapter constructor(private var players: List<PlayerModel>) :
        RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            return MainHolder(
                LayoutInflater.from(parent?.context).inflate(
                    R.layout.card_player,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val player = players[holder.adapterPosition]
            holder.bind(player)
        }

        override fun getItemCount(): Int = players.size

        class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(player: PlayerModel) {
                itemView.playerName.text = player.name
                itemView.number.text = player.number
            }
        }
    }

}
