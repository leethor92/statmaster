package views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.activity_player.*
import models.PlayerModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.statmaster.R
import org.wit.statmaster.activities.PlayerPresenter

class PlayerView : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: PlayerPresenter
    var player = PlayerModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        toolbarPlayer.title = title
        setSupportActionBar(toolbarPlayer)

        presenter = PlayerPresenter(this)

        btnAdd.setOnClickListener() {
            if (playerName.text.toString().isEmpty()) {
                toast(R.string.enter_player_title)
            } else {
                presenter.doAddOrSave(playerName.text.toString(), number.text.toString())
            }
        }

        chooseImage.setOnClickListener { presenter.doSelectImage() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun showPlayer(player: PlayerModel) {
        playerName.setText(player.name)
        number.setText(player.number)
        playerImage.setImageBitmap(readImageFromPath(this, player.image))
        if (player.image != null) {
            chooseImage.setText(R.string.change_player_image)
        }
        btnAdd.setText(R.string.save_player)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancelPlayer -> {
                presenter.doCancel()
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (data != null) {
                presenter.doActivityResult(requestCode, data)
            }
    }

}