package views.teamlist

import com.google.firebase.auth.FirebaseAuth
import models.TeamModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView
import views.VIEW
import views.gamelist.GameListView
import views.settings.SettingsView
import views.team.TeamView

class TeamListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddTeam() {
    view?.startActivityForResult<TeamView>(0)
  }

  fun doGame() {
    view?.startActivityForResult<GameListView>(0)
  }

  fun doSettings() {
    view?.startActivityForResult<SettingsView>(0)
  }

  fun doEditTeam(team: TeamModel) {
    view?.startActivityForResult(view?.intentFor<TeamView>()?.putExtra("team_edit", team), 0)
  }

  fun loadTeamsSearch(containingString: String){
    view?.showTeams(app.teams.findAll().filter { it.name.toLowerCase().contains(containingString.toLowerCase()) })
  }

  fun loadTeams(){
    doAsync {
      val teams = app.teams.findAll()
      uiThread {
        view?.showTeams(teams)
      }
    }
  }

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.teams.clear()
    app.games.clear()
    app.players.clear()
    view?.navigateTo(VIEW.LOGIN)
  }
}