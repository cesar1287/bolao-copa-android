package comcesar1287.github.bolocopadomundo2018.views.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.firestore.auth.FirebaseAuthService
import comcesar1287.github.bolocopadomundo2018.firestore.auth.ServiceListener
import kotlinx.android.synthetic.main.activity_sign_in.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        ViewCompat.setTranslationZ(progressBar, 16f)
        progressBar.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, android.R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN)

        signInButton.setOnClickListener{
            if (emailEdit.text.isBlank() or passwordEdit.text.isBlank()){
                Snackbar.make(signUpButton, R.string.error_required_fields, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            it.isClickable = false
            emailEdit.isEnabled = false
            emailEdit.clearFocus()
            passwordEdit.isEnabled = false
            passwordEdit.clearFocus()

            progressBar.visibility = View.VISIBLE

            FirebaseAuthService(this).signWithEmailAndPassword(emailEdit.text.toString(), passwordEdit.text.toString(),
                object : ServiceListener {
                    override fun onAuthComplete() {
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onError(error: String?) {
                        Snackbar.make(signUpButton, error.orEmpty(), Snackbar.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                        it.isClickable = true
                        emailEdit.isEnabled = true
                        passwordEdit.isEnabled = true
                    }
                })
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        KeyboardVisibilityEvent.setEventListener(this, {
            if (it){
                focusOnView()
            }
        })
    }

    private fun focusOnView() {
        scrollView.smoothScrollTo(0, linearLayout.bottom)
    }
}
