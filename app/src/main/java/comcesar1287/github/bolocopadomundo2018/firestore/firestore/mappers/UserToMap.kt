package comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers

import com.google.firebase.firestore.FieldValue
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.documents.UsersDocument
import comcesar1287.github.bolocopadomundo2018.models.User
import java.util.*

class UserToMap : Mapper<User, Map<String, Any?>> {

    override fun map(from: User): Map<String, Any?> {

        val map = HashMap<String, Any?>()
        map[UsersDocument.NAME] = from.name
        map[UsersDocument.EMAIL] = from.email
        map[UsersDocument.BETS] = from.bets
        from.createdAt?.let {
            map[UsersDocument.CREATED_AT] = from.createdAt
        } ?: run { map[UsersDocument.CREATED_AT] = FieldValue.serverTimestamp() }

        map[UsersDocument.UPDATED_AT] = FieldValue.serverTimestamp()

        return map
    }
}