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
import kotlinx.android.synthetic.main.activity_game.playerRecyclerView
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.*
import org.wit.placemark.activities.GamePresenter
import org.wit.statmaster.R
import views.BaseView
import views.team.PlayerAdapter
import views.team.PlayerListener

class GameView : BaseView() , AnkoLogger, PlayerListener, AdapterView.OnItemSelectedListener {

    var game = GameModel()
    var team = TeamModel()

    private var allTeams: MutableList<String> = ArrayList()
    private var teamIds: MutableList<Long> = ArrayList()
    private var gameTeams: MutableList<TeamModel> = ArrayList()
    private var players: MutableList<PlayerModel> = ArrayList()

    lateinit var presenter: GamePresenter
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        presenter = initPresenter (GamePresenter(this)) as GamePresenter

        spinner = this.teamList
        spinner.setOnItemSelectedListener(this)


        val layoutManager = LinearLayoutManager(this)
        playerRecyclerView.layoutManager = layoutManager

      presenter.loadTeams()
      presenter.loadPlayers()

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
        showPlayers(game.players)
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
                    presenter.doAddOrSave(gameTitle.text.toString(), score.text.toString(), winCheckbox.isChecked, drawCheckbox.isChecked, lossCheckbox.isChecked,
                        spinnerText.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

  override fun onPlayerClick(player: PlayerModel, game: GameModel) {
    presenter.doEditPlayer(player, presenter.game)
  }

  override fun showPlayers (players: MutableList<PlayerModel>) {
    playerRecyclerView.adapter = PlayerAdapter(presenter.game.players, this)
    playerRecyclerView.adapter?.notifyDataSetChanged()
  }

  override fun showTeams(teams: List<TeamModel>) {
    for (i in teams){
      gameTeams.add(i)
    }
    allTeams.add("Select a team")
    teamIds.add(0)

    teams.forEach {
      allTeams.add(it.name)
      teamIds.add(it.id)
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

      var teamId = teamIds[position]
      presenter.game.teamId = teamId

        for (t in gameTeams) {

          if (t.id == teamIds[position]) {
            for (i in t.players) {
              i.gameId = presenter.game.id
              players.add(i)
            }
            presenter.game.players = players
          }
        }
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadTeams()
    presenter.loadPlayers()
    super.onActivityResult(requestCode, resultCode, data)
  }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

}
