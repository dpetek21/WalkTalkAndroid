package hr.foi.rampu.walktalk.firebaseHandler
import com.google.firebase.firestore.FirebaseFirestore


class LoginRegisterHandler {
    val db = FirebaseFirestore.getInstance()
    val users= db.collection("users")
    fun addUser(userId: String, userData: Map<String, Any>) {
        users.document(userId)
            .set(userData)
            .addOnSuccessListener { documentReference ->
                println("Document added with ID: $documentReference.id")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
    fun getUser(userId: String, callback: (Map<String, Any>?) -> Unit) {
        users.document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.data
                    callback(userData)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                println("Error getting document: $e")
                callback(null)
            }
    }
    fun checkLogin(userId: String, password: String): Boolean{
        var isgood=0
        //var usermap: Map<String,Any>
        users
            .whereEqualTo("email", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {

                     val user = querySnapshot.documents[0]
                     val usermap = user.data as Map<String,Any>
                    if(usermap[password]==password){
                        isgood++
                    }
                } else {
                }
            }
            .addOnFailureListener { e ->
            }
        if(isgood==1) {
            return true
        }
        return false
    }
}