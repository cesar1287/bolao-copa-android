package comcesar1287.github.bolocopadomundo2018.views.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.preferences.MainPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
                message.setText(R.string.title_leaderboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                message.setText(R.string.title_profile)
                MainPreference.cleanPreference(this)
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (MainPreference.getUserReference(this) == null) {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
            return
        }
    }
}
