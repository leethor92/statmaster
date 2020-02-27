package org.wit.statmaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game_list.*
import main.MainApp
import models.GameModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.statmaster.R

class GameListActivity : AppCompatActivity(), GameListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadGames()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<GameActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGameClick(game: GameModel) {
        startActivityForResult(intentFor<GameActivity>().putExtra("game_edit", game), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadGames()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadGames() {
        showGames(app.games.findAll())
    }

    fun showGames (games: List<GameModel>) {
        recyclerView.adapter = GameAdapter(games, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}