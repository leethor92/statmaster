package views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.init
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
    }
}
