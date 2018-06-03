package comcesar1287.github.bolocopadomundo2018.models

import com.google.firebase.firestore.DocumentReference

data class User(var name:String? = null,
                var email:String? = null,
                var bets: DocumentReference? = null) : BaseModel()