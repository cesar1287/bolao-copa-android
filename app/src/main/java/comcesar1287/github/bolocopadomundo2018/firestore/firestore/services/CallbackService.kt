package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

interface CallbackService<T>{
    fun onComplete(item: T?)
}