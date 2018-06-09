package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers.BetToMap
import comcesar1287.github.bolocopadomundo2018.models.Bet

class BetService: FirestoreService<Bet> {

    override val collection: String
        get() = "bets"

    override fun add(item: Bet) {
        val id = if (item.id != null) item.id else db.collection(collection).document().id
        val map = BetToMap().map(item)

        db.collection(collection)
                .document(id.orEmpty())
                .set(map)
                .addOnFailureListener { exception ->
                    exception.localizedMessage
                }
    }

    override fun remove(item: Bet) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByReference(reference: DocumentReference, callbackService: CallbackService<Bet>) {
        reference.addSnapshotListener{ documentSnapshot, _ ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        val bet = documentSnapshot.toObject(Bet::class.java)
                        bet?.id = documentSnapshot.id
                        callbackService.onComplete(bet)
                    } else
                        callbackService.onComplete(null)
                }
    }

    override fun remove(query: Query) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun querySnapshot(query: Query, callbackService: CallbackService<List<Bet>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(query: Query, callbackService: CallbackService<List<Bet>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}