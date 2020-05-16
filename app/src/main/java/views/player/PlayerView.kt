package views.player

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_player.*
import models.GameModel
import models.PlayerModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.statmaster.R
import views.BaseView
import java.lang.Double
import kotlin.math.round

class PlayerView : BaseView(), AnkoLogger {

    lateinit var presenter: PlayerPresenter
    var player = PlayerModel()
    var game = GameModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        super.init(toolbarPlayer, false);

        presenter = initPresenter (PlayerPresenter(this)) as PlayerPresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        add_point.setOnClickListener {
            var points = Integer.parseInt(totalPoints.text.toString())
            points++
            accuracyCalc()

            totalPoints.setText(points.toString())
            accuracy.setText( player.accuracy.toString())
        }

        minus_point.setOnClickListener {
            var points = Integer.parseInt(totalPoints.text.toString())
            if (points > 0) {
                points--
            }

            accuracyCalc()

            totalPoints.setText(points.toString())
            accuracy.setText( player.accuracy.toString())
        }

        add_goal.setOnClickListener {
            var goals = Integer.parseInt(totalGoals.text.toString())
            goals++

            accuracyCalc()

            totalGoals.setText(goals.toString())
            accuracy.setText( player.accuracy.toString())
        }

        minus_goal.setOnClickListener {
            var goals = Integer.parseInt(totalGoals.text.toString())
            if (goals > 0) {
                goals--
            }
            accuracyCalc()

            totalGoals.setText(goals.toString())
            accuracy.setText( player.accuracy.toString())
        }

        add_wide.setOnClickListener {
            var wides =  Integer.parseInt(totalWides.text.toString())
            wides++
            accuracyCalc()

            totalWides.setText(wides.toString())
            accuracy.setText( player.accuracy.toString())
        }

        minus_wide.setOnClickListener {
            var wides =  Integer.parseInt(totalWides.text.toString())
            if (wides > 0) {
                wides--
            }

            accuracyCalc()

            totalWides.setText(wides.toString())
            accuracy.setText( player.accuracy.toString())
        }

        add_pass.setOnClickListener {
            var passes = Integer.parseInt(total_passes.text.toString())
            passes++
            passingAccCalc()

            total_passes.setText(passes.toString())
            passingAccuracy.setText(player.passingAcc.toString())
        }

        minus_pass.setOnClickListener {
            var passes = Integer.parseInt(total_passes.text.toString())
            if (passes > 0) {
                passes--
            }

            passingAccCalc()

            total_passes.setText(passes.toString())
            passingAccuracy.setText(player.passingAcc.toString())
        }

        add_missed_pass.setOnClickListener {
            var missedPasses = Integer.parseInt(total_missed_passes.text.toString())
            missedPasses++
            passingAccCalc()

            total_missed_passes.setText(missedPasses.toString())
            passingAccuracy.setText(player.passingAcc.toString())
        }

        minus_missed_pass.setOnClickListener {
            var missedPasses = Integer.parseInt(total_missed_passes.text.toString())
            if (missedPasses > 0) {
                missedPasses--
            }

            passingAccCalc()

            total_missed_passes.setText(missedPasses.toString())
            passingAccuracy.setText(player.passingAcc.toString())
        }

        add_possession.setOnClickListener {
            var possessions =  Integer.parseInt(total_possessions.text.toString())
            possessions++
            ballRetentionCalc(player)

            total_possessions.setText(possessions.toString())
            retention.setText(player.ballRetention.toString())
        }

        minus_possession.setOnClickListener {
            var possessions =  Integer.parseInt(total_possessions.text.toString())
            if (possessions > 0) {
                possessions--
            }

            ballRetentionCalc(player)

            total_possessions.setText(possessions.toString())
            retention.setText(player.ballRetention.toString())
        }

        add_lost_possession.setOnClickListener {
            var lostPossessions =  Integer.parseInt(totalLostPossessions.text.toString())
            lostPossessions++
            ballRetentionCalc(player)

            totalLostPossessions.setText(lostPossessions.toString())
            retention.setText(player.ballRetention.toString())
        }

        minus_lost_possession.setOnClickListener {
            var lostPossessions =  Integer.parseInt(totalLostPossessions.text.toString())
            if (lostPossessions > 0) {
                lostPossessions--
            }

            ballRetentionCalc(player)

            totalLostPossessions.setText(lostPossessions.toString())
            retention.setText(player.ballRetention.toString())
        }
    }



    override fun showPlayer(player: PlayerModel) {
        playerName.setText(player.name)
        number.setText(player.number)
        totalPoints.setText(player.point.toString())
        totalGoals.setText(player.goal.toString())
        totalWides.setText(player.wide.toString())
        total_possessions.setText(player.possession.toString())
        total_passes.setText(player.pass.toString())
        totalLostPossessions.setText(player.lostPossession.toString())
        total_missed_passes.setText(player.missedPass.toString())
        accuracy.setText(player.accuracy.toString())
        passingAccuracy.setText(player.passingAcc.toString())
        retention.setText(player.ballRetention.toString())

        Glide.with(this).load(player.image).into(playerImage);
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
                    presenter.doAddOrSave(playerName.text.toString(), number.text.toString(), Integer.parseInt(totalPoints.text.toString()), Integer.parseInt(totalGoals.text.toString()),
                        Integer.parseInt(totalWides.text.toString()), Integer.parseInt(total_passes.text.toString()), Integer.parseInt(total_possessions.text.toString()),
                        Integer.parseInt(totalLostPossessions.text.toString()), Integer.parseInt(total_missed_passes.text.toString()), Double.parseDouble(accuracy.text.toString()),
                        Double.parseDouble(passingAccuracy.text.toString()), Double.parseDouble(retention.text.toString()))
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

    private fun accuracyCalc() {
        var points = Integer.parseInt(totalPoints.text.toString())
        var goals = Integer.parseInt(totalGoals.text.toString())
        var wides = Integer.parseInt(totalWides.text.toString())

        player.accuracy = round((points.toDouble() + goals.toDouble()) / (goals.toDouble() + points.toDouble() + wides.toDouble()) * 100.toDouble())
    }

    private fun passingAccCalc() {
        var passes = Integer.parseInt(total_passes.text.toString())
        var missedPasses = Integer.parseInt(total_missed_passes.text.toString())
        player.passingAcc = round(passes.toDouble() / (passes.toDouble() + missedPasses.toDouble()) * 100.toDouble())
    }

    private fun ballRetentionCalc(player: PlayerModel) {
        var possessions = Integer.parseInt(total_possessions.text.toString())
        var lostPossessions = Integer.parseInt(totalLostPossessions.text.toString())
        player.ballRetention = round(possessions.toDouble() / (possessions.toDouble() + lostPossessions.toDouble()) * 100.toDouble())
    }

}