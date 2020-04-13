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

class GameListView : AppCompatActivity(), GameListener {

    lateinit var presenter: GameListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)
        toolbar.title = title
        setSupportActionBar(toolbar)

        presenter = GameListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            GameAdapter(presenter.getGames(), this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddGame()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGameClick(game: GameModel) {
        presenter.doEditGame(game)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}