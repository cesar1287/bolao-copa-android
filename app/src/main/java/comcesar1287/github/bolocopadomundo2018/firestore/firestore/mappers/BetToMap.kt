package comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers

import com.google.firebase.firestore.FieldValue
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.documents.BetsDocument
import comcesar1287.github.bolocopadomundo2018.models.Bet
import java.util.*

class BetToMap : Mapper<Bet, Map<String, Any?>> {

    override fun map(from: Bet): Map<String, Any?> {

        val map = HashMap<String, Any?>()
        map[BetsDocument.USER] = from.user
        map[BetsDocument.GROUPS] = from.groups
        map[BetsDocument.PLAYOFSS] = from.playoffs
        from.createdAt?.let {
            map[BetsDocument.CREATED_AT] = from.createdAt
        } ?: run { map[BetsDocument.CREATED_AT] = FieldValue.serverTimestamp() }

        map[BetsDocument.UPDATED_AT] = FieldValue.serverTimestamp()

        return map
    }
}