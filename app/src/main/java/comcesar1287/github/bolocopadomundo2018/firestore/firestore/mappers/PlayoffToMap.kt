package comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers

import com.google.firebase.firestore.FieldValue
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.documents.PlayoffsDocument
import comcesar1287.github.bolocopadomundo2018.models.Playoff
import java.util.*

class PlayoffToMap : Mapper<Playoff, Map<String, Any?>> {

    override fun map(from: Playoff): Map<String, Any?> {

        val map = HashMap<String, Any?>()
        map[PlayoffsDocument.TEAM_ONE] = from.teamOne
        map[PlayoffsDocument.TEAM_TWO] = from.teamTwo
        map[PlayoffsDocument.WINNER] = from.winner
        map[PlayoffsDocument.PHASE] = from.phase
        from.createdAt?.let {
            map[PlayoffsDocument.CREATED_AT] = from.createdAt
        } ?: run { map[PlayoffsDocument.CREATED_AT] = FieldValue.serverTimestamp() }

        map[PlayoffsDocument.UPDATED_AT] = FieldValue.serverTimestamp()

        return map
    }
}