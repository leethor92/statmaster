package views.player

import android.content.Intent
import helpers.showImagePicker
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView
import views.IMAGE_REQUEST


class PlayerPresenter(view: BaseView) : BasePresenter(view) {

    var player = PlayerModel()
    var game = GameModel()
    var team = TeamModel()

    var edit = false;
    var gameEdit = false;
    private var gamePlayers: MutableList<PlayerModel> = ArrayList()

    init {
        if (view.intent.hasExtra("player_edit") && view.intent.hasExtra("team_data")!!) {
            edit = true
            player = view.intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            team = view.intent.extras?.getParcelable<TeamModel>("team_data")!!
            view.showPlayer(player)
        }
        else if (view.intent.hasExtra("game_data"))
        {

            gameEdit = true
            player = view.intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            game = view.intent.extras?.getParcelable<GameModel>("game_data")!!
            gamePlayers = game.players

            view.showPlayer(player)
        }
        else
        {
            team = view.intent.extras?.getParcelable<TeamModel>("team_data")!!
        }

    }

    fun doAddOrSave(name: String, number: String, points: Int, goals: Int, wides: Int, possessions: Int, passes: Int, missedPasses: Int, lostPossessions: Int, accuracy: Double, passingAcc: Double, retention: Double) {
        player.name = name
        player.number = number
        player.point = points
        player.goal = goals
        player.wide = wides
        player.possession = possessions
        player.pass = passes
        if (gameEdit)
        {
            player.teamId = team.id
            player.gameId = game.id
        } else {
            player.teamId = team.id
        }
        player.lostPossession = lostPossessions
        player.missedPass = missedPasses
        player.accuracy = accuracy
        player.ballRetention = retention
        player.passingAcc = passingAcc

        doAsync {
        if (edit) {
            app.players.update(player)
        } /*else if (gameEdit)
        {
            var count = 0
            for (i in gamePlayers) {
                count++
                if (i.id == player.id)
                {
                    var listPos = count
                    game.players.set(listPos, player)
                    break
                }
            }

        }*/
        else {
            app.players.create(player)
        }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {

           /* if (gameEdit) {
                var count = 0
                for (i in gamePlayers) {
                    count++
                    if (i.id == player.id)
                    {
                        var listPos = count
                        game.players.removeAt(listPos)
                        break
                    }
                }
            }
            else {*/
                app.players.delete(player)
            //}
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doActivityResult(requestCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                player.image = data.data.toString()
                view?.showPlayer(player)
            }
        }
    }
}