package org.wit.statmaster.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.wit.statmaster.R
import views.gamelist.GameListView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.activity_splash)

        // Using a handler to delay loading the MainActivity
        Handler().postDelayed({

            startActivity(Intent(this, GameListView::class.java))

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            finish()
        }, 2000)
    }

    private fun makeFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }
}