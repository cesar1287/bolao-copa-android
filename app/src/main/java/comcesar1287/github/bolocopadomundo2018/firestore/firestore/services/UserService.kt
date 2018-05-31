package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.documents.UsersDocument
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers.UserToMap
import comcesar1287.github.bolocopadomundo2018.models.User

class UserService : FirestoreService<User> {

    override val collection: String
        get() = "users"

    override fun add(item: User) {
        val id = if (item.id != null) item.id else db.collection(collection).document().id
        val map = UserToMap().map(item)

        db.collection(collection)
                .document(id.orEmpty())
                .set(map)
                .addOnFailureListener { exception ->
                    exception.localizedMessage
                }
    }

    override fun remove(item: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByReference(reference: DocumentReference, callbackService: CallbackService<User>) {
        reference
            .addSnapshotListener{ documentSnapshot, _ ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    user?.id = documentSnapshot.id
                    callbackService.onComplete(user)
                } else
                    callbackService.onComplete(null)
            }
    }

    fun findByEmail(email: String, callbackService: CallbackService<List<User>>){
        val userService = UserService()
        val querySelect = userService
                .collectionReference
                .whereEqualTo(UsersDocument.EMAIL, email)
        query(querySelect, object : CallbackService<List<User>> {
                    override fun onComplete(item: List<User>?) {
                        callbackService.onComplete(item)
                    }
                }
        )

    }

    fun findByUid(uid: String?, callbackService: CallbackService<Boolean>) {
        val userService = UserService()
        uid?.let {
            userService.collectionReference.document(it).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        if (it.exists()) {
                            callbackService.onComplete(true)
                        } else {
                            callbackService.onComplete(false)
                        }
                    } ?: run {
                        callbackService.onComplete(false)
                    }
                }
            }
        }
    }

    override fun remove(query: Query) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun querySnapshot(query: Query, callbackService: CallbackService<List<User>>) {
        val result = ArrayList<User>()
        query.addSnapshotListener{ querySnapshot, _ ->
            querySnapshot?.forEach {
                val user = it.toObject(User::class.java)
                user.id = it.id
                result.add(user)
            }
            callbackService.onComplete(result)
        }
    }

    override fun query(query: Query, callbackService: CallbackService<List<User>>) {
        val result = ArrayList<User>()
        query.get().addOnSuccessListener {
            querySnapshot ->
            querySnapshot.forEach {
                val user = it.toObject(User::class.java)
                user.id = it.id
                result.add(user)
            }
            callbackService.onComplete(result)
        }
    }
}