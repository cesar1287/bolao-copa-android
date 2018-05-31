package comcesar1287.github.bolocopadomundo2018.models

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

open class BaseModel : Serializable {
    var id: String? = null
    var createdAt: Date? = null

    @ServerTimestamp
    var updatedAt: Date? = null
}