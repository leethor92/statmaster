package org.wit.statmaster.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.recyclerView1
import kotlinx.android.synthetic.main.activity_team.*
import main.MainApp
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.*
import org.wit.placemark.activities.GamePresenter
import org.wit.statmaster.R
import views.BaseView
import views.team.PlayerAdapter
import views.teamlist.TeamAdapter

class GameView : BaseView() , AnkoLogger, AdapterView.OnItemSelectedListener {

    var game = GameModel()
    var team = TeamModel()

    private var allTeams: MutableList<String> = ArrayList()

    lateinit var presenter: GamePresenter
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        presenter = initPresenter (GamePresenter(this)) as GamePresenter

        spinner = this.teamList
        spinner.setOnItemSelectedListener(this)

        presenter.loadTeams()

        super.init(toolbarAdd, true);
    }

    override fun showGame(game: GameModel, totalPlayerGoals: Int, totalPlayerPoints: Int){
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

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadTeams()
    super.onActivityResult(requestCode, resultCode, data)
  }


  override fun showTeams(teams: List<TeamModel>) {
    teams.forEach {
      allTeams.add(it.name)

    }

    val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, allTeams)

    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    spinner.setAdapter(aa)
  }


    override fun getTotalPlayerGoals(players: List<PlayerModel>): Int {
        return players.filter { it.gameId == presenter.game.id }.sumBy { it.goal }
    }

    override fun getTotalPlayerPoints(players: List<PlayerModel>): Int {
        return players.filter { it.gameId == presenter.game.id }.sumBy { it.point }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        spinnerText!!.text = "Selected : " + allTeams[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}
