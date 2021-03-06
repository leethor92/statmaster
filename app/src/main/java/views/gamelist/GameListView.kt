package views.gamelist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_game_list.*
import models.GameModel
import org.wit.statmaster.R
import views.BaseView

class GameListView : BaseView(), GameListener {

  lateinit var presenter: GameListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_game_list)

    init(toolbar, false)

    presenter = initPresenter(GameListPresenter(this)) as GameListPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadGames()

    val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNav)
    bottomNavView.setOnNavigationItemSelectedListener { menuItem -> onOptionsItemSelected(menuItem) }

    bottomNavView.menu.findItem(R.id.item_game).isChecked = true
  }

  override fun showGames(games: List<GameModel>) {
    recyclerView.adapter = GameAdapter(games, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)

    val searchView: SearchView = menu?.findItem(R.id.item_search)?.actionView as SearchView
    searchView.queryHint = "Search for a Game"
    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
      override fun onQueryTextChange(newText: String): Boolean {
        presenter.loadGamesSearch(newText!!)
        return false
      }

      override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isBlank() || query.isEmpty()) presenter.loadGames()
        else presenter.loadGamesSearch(query)
        return false
      }
    })

    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddGame()
      R.id.item_team -> {
        presenter.doTeams()
        return true
      }
      R.id.item_logout -> {
        presenter.doLogout()
        return true
      }
      R.id.item_user -> {
        presenter.doSettings()
        return true
      }
      R.id.item_wonGames -> {
        item.isChecked = !item.isChecked
        presenter.doShowWonGames(item.isChecked)
      }
      R.id.item_drawnGames -> {
        item.isChecked = !item.isChecked
        presenter.doShowDrawnGames(item.isChecked)
      }
      R.id.item_lostGames -> {
        item.isChecked = !item.isChecked
        presenter.doShowLostGames(item.isChecked)
      }
    }

    return super.onOptionsItemSelected(item)
  }

  override fun onGameClick(game: GameModel) {
    presenter.doEditGame(game)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadGames()
    super.onActivityResult(requestCode, resultCode, data)
  }

}