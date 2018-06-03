package comcesar1287.github.bolocopadomundo2018.views.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.preferences.MainPreference
import comcesar1287.github.bolocopadomundo2018.views.fragments.HomeFragment
import comcesar1287.github.bolocopadomundo2018.views.fragments.LeaderboardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.content, HomeFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.content, LeaderboardFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                //message.setText(R.string.title_profile)
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

        supportFragmentManager.inTransaction {
            replace(R.id.content, HomeFragment())
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }
}
