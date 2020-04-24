package org.wit.statmaster.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.card_player.*
import kotlinx.android.synthetic.main.card_player.view.*
import main.MainApp
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.*
import org.wit.placemark.activities.GamePresenter
import org.wit.statmaster.R
import views.BaseView
import views.game.PlayerAdapter
import views.game.PlayerListener
import kotlin.math.round

class GameView : BaseView() , AnkoLogger, PlayerListener {

    var game = GameModel()
    lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        init(toolbarAdd)

        presenter = initPresenter (GamePresenter(this)) as GamePresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        presenter.loadPlayers()
    }

    override fun showGame(game: GameModel) {
        gameTitle.setText(game.title)
        score.setText(game.score)
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
            R.id.item_save -> {
                if (gameTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_game_title)
                } else {
                    presenter.doAddOrSave(gameTitle.text.toString(), score.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel) {
        presenter.doEditPlayer(player)
    }

    override fun showPlayers (players: List<PlayerModel>) {
        recyclerView1.adapter = PlayerAdapter(players, this)
        recyclerView1.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadPlayers()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
