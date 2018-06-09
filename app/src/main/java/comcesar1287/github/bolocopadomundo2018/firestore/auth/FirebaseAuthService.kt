package comcesar1287.github.bolocopadomundo2018.firestore.auth

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.GeneralService
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.CallbackService
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.UserService
import comcesar1287.github.bolocopadomundo2018.models.User
import comcesar1287.github.bolocopadomundo2018.preferences.MainPreference

class FirebaseAuthService(private var context: Activity) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var betDocumentReference: DocumentReference? = null

    fun register(name: String, email: String, password: String, serviceListener: ServiceListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        try {
                            val uid = mAuth.currentUser?.uid

                            uid?.let {
                                betDocumentReference = GeneralService().addUser(uid, name, email)
                                signWithEmailAndPassword(name, email, password, serviceListener)
                            } ?: run {
                                serviceListener.onError(context.getString(R.string.unknown_error))
                            }
                        } catch (exception: Exception) {
                            serviceListener.onError(context.getString(R.string.error_default))
                        }
                    } else {
                        try {
                            when (task.exception) {
                                is FirebaseAuthWeakPasswordException -> {
                                    serviceListener.onError(context.getString(R.string.error_password_too_weak))
                                }
                                is FirebaseAuthInvalidCredentialsException -> {
                                    serviceListener.onError(context.getString(R.string.error_email_invalid))
                                }
                                is FirebaseAuthUserCollisionException -> {
                                    serviceListener.onError(context.getString(R.string.error_user_already_registered))
                                }
                                else -> serviceListener.onError(context.getString(R.string.unknown_error))
                            }
                        } catch (exception: Exception) {
                            serviceListener.onError(context.getString(R.string.error_default))
                        }
                    }
                }
    }

    fun signWithEmailAndPassword(email: String, password: String, serviceListener: ServiceListener) {
        signWithEmailAndPassword("", email, password, serviceListener)
    }

    private fun signWithEmailAndPassword(name: String, email: String, password: String, serviceListener: ServiceListener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                when {
                    task.exception is FirebaseAuthInvalidUserException -> {
                        serviceListener.onError(context.getString(R.string.error_user_password_invalid))
                    }
                    task.exception is FirebaseAuthInvalidCredentialsException -> {
                        serviceListener.onError(context.getString(R.string.error_user_password_invalid))
                    }
                    else -> {
                        serviceListener.onError(context.getString(R.string.unknown_error))
                    }
                }

                return@OnCompleteListener
            }

            val user = User()
            user.id = mAuth.currentUser?.uid
            GeneralService().findEmployeeByUID(user, object : CallbackService<User> {
                override fun onComplete(item: User?) {
                    item?.let {
                        val userDocumentReference = UserService().collectionReference.document(user.id!!)
                        betDocumentReference?.let {
                            saveInPreferences(userDocumentReference, betDocumentReference!!)
                        } ?: run {
                            saveInPreferences(userDocumentReference, item.bets!!)
                        }
                        serviceListener.onAuthComplete()
                    } ?: run {
                        serviceListener.onError(context.getString(R.string.unknown_error))
                    }
                }
            })
        })
    }

    fun saveInPreferences(userDocumentReference: DocumentReference, betDocumentReference: DocumentReference) {
        MainPreference.setUserReference(context, userDocumentReference.path)
        MainPreference.setBetReference(context, betDocumentReference.path)
    }
}