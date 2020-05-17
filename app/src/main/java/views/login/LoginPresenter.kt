package views.login

import com.google.firebase.auth.FirebaseAuth
import models.firebase.GameFireStore
import models.firebase.PlayerFireStore
import models.firebase.TeamFireStore
import org.jetbrains.anko.toast
import views.BasePresenter
import views.BaseView
import views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var gameFireStore: GameFireStore? = null
    var playerFireStore: PlayerFireStore? = null
    var teamFireStore: TeamFireStore? = null

    init {

        if (app.teams is TeamFireStore) {
            teamFireStore = app.teams as TeamFireStore
        }

        if (app.games is GameFireStore )  {
            gameFireStore = app.games as GameFireStore
        }

        if (app.players is PlayerFireStore) {
            playerFireStore = app.players as PlayerFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (gameFireStore != null) {
                    gameFireStore!!.fetchGames {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                }
                else
                {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }

                if (playerFireStore != null ) {
                    playerFireStore!!.fetchPlayers {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                }
                else
                {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }

                if (teamFireStore != null ) {
                    teamFireStore!!.fetchTeams {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                }
                else
                {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }

            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }
}