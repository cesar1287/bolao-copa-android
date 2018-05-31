package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import comcesar1287.github.bolocopadomundo2018.models.BaseModel

interface FirestoreService<T: BaseModel> {

    val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    val collection: String

    val collectionReference: CollectionReference
        get() = db.collection(collection)

    val id:String
        get() = db.collection(collection).document().id

    fun add(item: T)
    fun remove(item: T)
    fun findByReference(reference: DocumentReference, callbackService: CallbackService<T>)
    fun remove(query: Query)
    fun querySnapshot(query: Query, callbackService: CallbackService<List<T>>)
    fun query(query: Query, callbackService: CallbackService<List<T>>)

}