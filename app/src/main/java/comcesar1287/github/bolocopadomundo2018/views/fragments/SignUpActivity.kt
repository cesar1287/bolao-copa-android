package comcesar1287.github.bolocopadomundo2018.views.fragments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import comcesar1287.github.bolocopadomundo2018.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressBar.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, android.R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN)
        ViewCompat.setTranslationZ(progressBar, 16f)

        signUpButton.setOnClickListener {
            if (nameEdit.text.isBlank().or(emailEdit.text.isBlank().or(passwordEdit.text.isBlank()))){
                Snackbar.make(signInButton, R.string.error_required_fields, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            signUpButton.isClickable = false
            signInButton.isClickable = false
        }


        KeyboardVisibilityEvent.setEventListener(this,{
            if (it){
                focusOnView()
            }
        })
    }

    private fun focusOnView() {
        scrollView.smoothScrollTo(0, linearLayout.top)
    }
}
