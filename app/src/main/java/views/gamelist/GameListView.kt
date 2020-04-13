package views.gamelist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game_list.*
import models.GameModel
import org.wit.statmaster.R
import views.BaseView

class GameListView : BaseView(), GameListener {

    lateinit var presenter: GameListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        setSupportActionBar(toolbar)

        presenter = initPresenter(GameListPresenter(this)) as GameListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadGames()
    }

    override fun showGames(games: List<GameModel>) {
        recyclerView.adapter = GameAdapter(games, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddGame()
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