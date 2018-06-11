package comcesar1287.github.bolocopadomundo2018.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.CallbackService
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.UserService
import comcesar1287.github.bolocopadomundo2018.models.User
import comcesar1287.github.bolocopadomundo2018.preferences.MainPreference
import comcesar1287.github.bolocopadomundo2018.views.activities.SignInActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val userService = UserService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDocumentReference = userService.db.document(MainPreference.getUserReference(context!!))

        userService.findByReference(userDocumentReference, object : CallbackService<User>{
            override fun onComplete(item: User?) {
                item?.let {
                    name?.text = it.name
                    emailEdit?.text = Editable.Factory.getInstance().newEditable(it.email)
                }
            }
        })

        logout.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(context!!)
            alertDialogBuilder
                    .setMessage(R.string.do_you_really_want_logout)
                    .setPositiveButton(R.string.yes, { _, _ ->
                        MainPreference.cleanPreference(activity)
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(activity, SignInActivity::class.java))
                        activity?.finish()
                    })
                    .setNegativeButton(R.string.no, null)
                    .show()
        }
    }
}
