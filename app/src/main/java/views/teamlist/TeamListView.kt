package views.teamlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_team_list.*
import models.TeamModel
import org.wit.statmaster.R
import views.BaseView

class TeamListView : BaseView(), TeamListener {

  lateinit var presenter: TeamListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_team_list)

    init(toolbar, false)

    presenter = initPresenter(TeamListPresenter(this)) as TeamListPresenter

    val layoutManager = LinearLayoutManager(this)
    teamRecyclerView.layoutManager = layoutManager
    presenter.loadTeams()

    val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNav)
    bottomNavView.setOnNavigationItemSelectedListener { menuItem -> onOptionsItemSelected(menuItem) }
  }

  override fun showTeams(teams: List<TeamModel>) {
    teamRecyclerView.adapter = TeamAdapter(teams, this)
    teamRecyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_teamlist, menu)

    val searchView: SearchView = menu?.findItem(R.id.item_search)?.actionView as SearchView
    searchView.queryHint = "Search for a Team"
    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
      override fun onQueryTextChange(newText: String): Boolean {
        presenter.loadTeamsSearch(newText)
        return false
      }

      override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isBlank() || query.isEmpty()) presenter.loadTeams()
        else presenter.loadTeamsSearch(query)
        return false
      }
    })

    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_add -> presenter.doAddTeam()
      R.id.item_logout -> presenter.doLogout()
      R.id.item_user ->  presenter.doSettings()
      R.id.item_game -> presenter.doGame()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onTeamClick(team: TeamModel) {
    presenter.doEditTeam(team)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadTeams()
    super.onActivityResult(requestCode, resultCode, data)
  }

}