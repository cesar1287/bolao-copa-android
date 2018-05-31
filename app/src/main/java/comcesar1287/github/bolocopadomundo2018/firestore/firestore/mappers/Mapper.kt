package comcesar1287.github.bolocopadomundo2018.firestore.firestore.mappers

interface Mapper<From, To> {
    fun map(from: From): To
}