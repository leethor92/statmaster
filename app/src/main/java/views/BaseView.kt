package views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import models.GameModel
import models.PlayerModel
import models.TeamModel
import org.jetbrains.anko.AnkoLogger
import org.wit.statmaster.activities.GameView
import views.login.LoginView
import views.player.PlayerView
import views.settings.SettingsView
import views.team.TeamView
import views.teamlist.TeamListView

val IMAGE_REQUEST = 1

enum class VIEW {
    TEAMLIST, TEAM, GAME, PLAYER, LOGIN, SETTINGS
}

abstract class BaseView() : AppCompatActivity(), AnkoLogger {
    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, TeamListView::class.java)
        when (view) {
            VIEW.TEAM -> intent = Intent(this, TeamView::class.java)
            VIEW.TEAMLIST -> intent = Intent(this, TeamListView::class.java)
            VIEW.GAME -> intent = Intent(this, GameView::class.java)
            VIEW.PLAYER -> intent = Intent(this, PlayerView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
            VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showTeam(team: TeamModel) {}
    open fun showTeams(teams: List<TeamModel>) {}
    open fun showGame(game: GameModel, totalPlayerGoals: Int, totalPlayerPoints: Int) {}
    open fun showGames(games: List<GameModel>) {}
    open fun showPlayer(player: PlayerModel) {}
    open fun showPlayers(players: List<PlayerModel>) {}
    open fun getTotalPlayerGoals(players: List<PlayerModel> ) :Int { return 0}
    open fun getTotalPlayerPoints(players: List<PlayerModel>) :Int { return 0}
    open fun showProgress() {}
    open fun hideProgress() {}
}