package views.player

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
import views.BasePresenter
import views.BaseView

class PlayerView : BaseView(), AnkoLogger {

    lateinit var presenter: PlayerPresenter
    var player = PlayerModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        init(toolbarPlayer)

        presenter = initPresenter (PlayerPresenter(this)) as PlayerPresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }
    }



    override fun showPlayer(player: PlayerModel) {
        playerName.setText(player.name)
        number.setText(player.number)
        points.setText(player.point.toString())
        goals.setText(player.goal.toString())
        wides.setText(player.wide.toString())
        possessions.setText(player.possession.toString())
        passess.setText(player.pass.toString())
        playerImage.setImageBitmap(readImageFromPath(this, player.image))
        if (player.image != null) {
            chooseImage.setText(R.string.change_player_image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancelPlayer -> {
                presenter.doCancel()
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                if (playerName.text.toString().isEmpty()) {
                    toast(R.string.enter_player_title)
                } else {
                    presenter.doAddOrSave(playerName.text.toString(), number.text.toString(), Integer.parseInt(points.text.toString()), Integer.parseInt(goals.text.toString()),
                        Integer.parseInt(wides.text.toString()), Integer.parseInt(passess.text.toString()), Integer.parseInt(possessions.text.toString()))
                }
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