package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers.PlayoffToMap
import comcesar1287.github.bolocopadomundo2018.models.Playoff

class PlayoffService: FirestoreService<Playoff> {

    override val collection: String
        get() = "playoffs"

    override fun add(item: Playoff) {
        val id = if (item.id != null) item.id else db.collection(collection).document().id
        val map = PlayoffToMap().map(item)

        db.collection(collection)
                .document(id.orEmpty())
                .set(map)
                .addOnFailureListener { exception ->
                    exception.localizedMessage
                }
    }

    override fun remove(item: Playoff) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByReference(reference: DocumentReference, callbackService: CallbackService<Playoff>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(query: Query) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun querySnapshot(query: Query, callbackService: CallbackService<List<Playoff>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(query: Query, callbackService: CallbackService<List<Playoff>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}