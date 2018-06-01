package comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers

import com.google.firebase.firestore.FieldValue
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.documents.GroupsDocument
import comcesar1287.github.bolocopadomundo2018.models.Group
import java.util.*

class GroupToMap : Mapper<Group, Map<String, Any?>> {

    override fun map(from: Group): Map<String, Any?> {

        val map = HashMap<String, Any?>()
        map[GroupsDocument.FIRST] = from.first
        map[GroupsDocument.SECOND] = from.second
        map[GroupsDocument.NUMBER] = from.number
        from.createdAt?.let {
            map[GroupsDocument.CREATED_AT] = from.createdAt
        } ?: run { map[GroupsDocument.CREATED_AT] = FieldValue.serverTimestamp() }

        map[GroupsDocument.UPDATED_AT] = FieldValue.serverTimestamp()

        return map
    }
}