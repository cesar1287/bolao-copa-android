package comcesar1287.github.bolocopadomundo2018.models

import com.google.firebase.firestore.DocumentReference

data class Bet(var user: DocumentReference? = null,
               var groups: List<DocumentReference>? = null,
               var playoffs: List<DocumentReference>? = null) :BaseModel()