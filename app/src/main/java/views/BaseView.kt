package views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.AnkoLogger
import org.wit.statmaster.activities.GameView
import views.gamelist.GameListView
import views.login.LoginView
import views.player.PlayerView

val IMAGE_REQUEST = 1

enum class VIEW {
    GAME, PLAYER, LIST, LOGIN
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {
    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, GameListView::class.java)
        when (view) {
            VIEW.GAME -> intent = Intent(this, GameView::class.java)
            VIEW.PLAYER -> intent = Intent(this, PlayerView::class.java)
            VIEW.LIST -> intent = Intent(this, GameListView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
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

    open fun showGame(game: GameModel) {}
    open fun showGames(games: List<GameModel>) {}
    open fun showPlayer(player: PlayerModel) {}
    open fun showPlayers(players: List<PlayerModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}