package comcesar1287.github.bolocopadomundo2018.firestore.auth

import android.app.Activity
import android.support.design.widget.Snackbar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.GeneralService
import comcesar1287.github.bolocopadomundo2018.R

class FirebaseAuthService(private var context: Activity) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(name: String, email: String, password: String, serviceListener: ServiceListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        try {
                            val uid = mAuth.currentUser?.uid

                            uid?.let {
                                GeneralService(context).addUser(uid, name, email)
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

//            val user = User()
//            user.email = email
//
//            GeneralService(context).findEmployeeByUser(user, object : CallbackService<EmployeeDTO> {
//                override fun onComplete(item: EmployeeDTO?) {
//                    item?.let {
//                        val employeeDTO = it
//
//                        if (it.employee?.role == Role.EMPLOYEE) {
//                            val uid = mAuth.currentUser?.uid
//
//                            GeneralService(context).findEmployeeByUID(it.user!!, object : CallbackService<User> {
//                                override fun onComplete(item: User?) {
//                                    item?.id = uid
//
//                                    uid?.let {
//                                        val db = FirebaseFirestore.getInstance()
//                                        db.collection(UserService().collection)
//                                                .document(it)
//                                                .set(item!!)
//                                                .addOnCompleteListener {
//                                                    val userDocumentReference = UserService().collectionReference.document(uid)
//
//                                                    if (it.isSuccessful) {
//                                                        saveInPreferences(employeeDTO, userDocumentReference.path)
//                                                        serviceListener.onAuthComplete()
//                                                    }
//                                                }
//                                    }
//                                }
//                            })
//                        } else {
//                            saveInPreferences(item, item.userReference?.path!!)
//                            serviceListener.onAuthComplete()
//                        }
//                    }
//                }
//            })
        })
    }

//    fun saveInPreferences(employeeDTO: EmployeeDTO, path: String) {
//        MainPreference.setUserReference(context, path)
//        MainPreference.setCompanyReference(context, employeeDTO.employee?.company?.path)
//        MainPreference.setEmployeeReference(context, employeeDTO.user?.jobs!![0].path)
//        try {
//            MainPreference.setShiftReference(context, employeeDTO.employee?.allocations?.last()!!.shift?.path)
//        } catch (e: KotlinNullPointerException) {
//            val db = FirebaseFirestore.getInstance()
//            val allocation = Allocation()
//
//            allocation.jobTitle = employeeDTO.employee?.allocation?.jobTitle
//            allocation.shift = employeeDTO.employee?.allocation?.shift
//            allocation.admissionDate = employeeDTO.employee?.allocation?.admissionDate
//
//            val employee: Employee? = employeeDTO.employee
//            employee?.allocations = listOf(allocation)
//
//            db.collection(EmployeeService().collection)
//                    .document(employeeDTO.employee?.id!!)
//                    .set(employee!!)
//        }
//        MainPreference.setEmployeeRole(context, employeeDTO.employee?.role!!)
//    }
//
//    fun forgotPassword(email: String, serviceListener: ServiceListener){
//        if (ConnectionUtils.isGooglePlayServicesAvailable(context)) {
//            mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    serviceListener.onAuthComplete()
//                } else {
//                    when (task.exception) {
//                        is FirebaseAuthInvalidUserException -> {
//                            serviceListener.onError(context.getString(R.string.error_email_not_found))
//                        }
//                        else -> serviceListener.onError(task.exception.let { it!!.message }
//                                ?: run { context.getString(R.string.error_default) })
//                    }
//                }
//            }
//        } else {
//            Snackbar.make(context.parent.signInButton, context.getString(R.string.google_play_services_not_installed),
//                    Snackbar.LENGTH_SHORT).show()
//        }
//    }
}