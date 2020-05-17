package org.wit.statmaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.*
import org.wit.placemark.activities.GamePresenter
import org.wit.statmaster.R
import views.BaseView
import views.team.PlayerAdapter
import views.team.PlayerListener

class GameView : BaseView() , AnkoLogger {

    var game = GameModel()
    lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        super.init(toolbarAdd, true);

        presenter = initPresenter (GamePresenter(this)) as GamePresenter
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
                else if ( (winCheckbox.isChecked && drawCheckbox.isChecked) || (winCheckbox.isChecked && lossCheckbox.isChecked)
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

    override fun getTotalPlayerGoals(players: List<PlayerModel>): Int {
        return players.filter { it.gameId == presenter.game.id }.sumBy { it.goal }
    }

    override fun getTotalPlayerPoints(players: List<PlayerModel>): Int {
        return players.filter { it.gameId == presenter.game.id }.sumBy { it.point }
    }
}
