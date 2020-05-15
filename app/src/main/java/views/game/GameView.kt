package org.wit.statmaster.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_list.*
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
import views.player.PlayerView
import kotlin.math.round

class GameView : BaseView() , AnkoLogger, PlayerListener {

    var game = GameModel()
    lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        super.init(toolbarAdd, true);

        presenter = initPresenter (GamePresenter(this)) as GamePresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        presenter.loadPlayers()

        addPlayer.setOnClickListener {
            startActivity(intentFor<PlayerView>().putExtra("game_data", presenter.game))
            finish()
        }
    }

    override fun showGame(game: GameModel, totalPlayerGoals: Int, totalPlayerPoints: Int ) {
        gameTitle.setText(game.title)
        score.setText(game.score)
        winCheckbox.isChecked = game.win
        drawCheckbox.isChecked = game.draw
        lossCheckbox.isChecked = game.loss
        gameGoals.text = game.goal
        gamePoints.text = game.point
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_match, menu)

        val searchView: SearchView = menu?.findItem(R.id.item_search)?.actionView as SearchView
        searchView.queryHint = "Search for a Player"
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.loadPlayersSearch(newText!!)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isBlank() || query.isEmpty()) presenter.loadPlayers()
                else presenter.loadPlayersSearch(query)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_save -> {
                if (gameTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_game_title)
                }
                else if ( (winCheckbox.isChecked && drawCheckbox.isChecked) || (lossCheckbox.isChecked)
                        || (drawCheckbox.isChecked && lossCheckbox.isChecked)){
                    toast(R.string.checkbox_error)
                }
                else {
                    presenter.doAddOrSave(gameTitle.text.toString(), score.text.toString(), winCheckbox.isChecked, drawCheckbox.isChecked, lossCheckbox.isChecked)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel, game: GameModel) {
        presenter.doEditPlayer(player, presenter.game)
    }

    override fun showPlayers (players: List<PlayerModel>) {
        recyclerView1.adapter = PlayerAdapter(players.filter {it.gameId == presenter.game.id }, this)
        recyclerView1.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadPlayers()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun getTotalPlayerGoals(players: List<PlayerModel>): Int {
        return players.filter { it.gameId == presenter.game.id }.sumBy { it.goal }
    }

    override fun getTotalPlayerPoints(players: List<PlayerModel>): Int {
        return players.filter { it.gameId == presenter.game.id }.sumBy { it.point }
    }
}
