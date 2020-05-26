package views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.init
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import main.MainApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.statmaster.R
import views.BaseView

class SettingsView: BaseView(), AnkoLogger {

    lateinit var presenter: SettingsPresenter

    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        super.init(toolbarSettings, true)

        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

        save_settings.setOnClickListener {
            info("Update Settings button pressed")
            if (!settingsEmail.text.isNotEmpty() && !settingsPassword.text.isNotEmpty()) {
                toast("Email and password are required")
            }
            else {
                presenter.doUpdateSettings(settingsEmail.text.toString(), settingsPassword.text.toString())
            }
        }

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavView.setOnNavigationItemSelectedListener { menuItem -> onOptionsItemSelected(menuItem) }

        bottomNavView.menu.findItem(R.id.item_user).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_team -> {
                presenter.doTeam()
                return true
            }
            R.id.item_logout -> {
                presenter.doLogout()
                return true
            }
            R.id.item_game -> {
                presenter.doGame()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
