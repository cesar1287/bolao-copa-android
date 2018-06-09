package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers.GroupToMap
import comcesar1287.github.bolocopadomundo2018.models.Bet
import comcesar1287.github.bolocopadomundo2018.models.Group

class GroupService: FirestoreService<Group> {

    override val collection: String
        get() = "groups"

    override fun add(item: Group) {
        val id = if (item.id != null) item.id else db.collection(collection).document().id
        val map = GroupToMap().map(item)

        db.collection(collection)
                .document(id.orEmpty())
                .set(map)
                .addOnFailureListener { exception ->
                    exception.localizedMessage
                }
    }

    override fun remove(item: Group) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByReference(reference: DocumentReference, callbackService: CallbackService<Group>) {
        reference.get().addOnCompleteListener {
            val result = it.result

            if (result != null && result.exists()) {
                val group = result.toObject(Group::class.java)
                group?.id = result.id
                callbackService.onComplete(group)
            } else
                callbackService.onComplete(null)
        }
    }

    override fun remove(query: Query) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun querySnapshot(query: Query, callbackService: CallbackService<List<Group>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(query: Query, callbackService: CallbackService<List<Group>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}