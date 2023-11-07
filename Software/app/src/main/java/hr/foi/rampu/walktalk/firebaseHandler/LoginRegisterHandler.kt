package hr.foi.rampu.walktalk.firebaseHandler
import com.google.firebase.firestore.FirebaseFirestore


class LoginRegisterHandler {
    val db = FirebaseFirestore.getInstance()
    val users= db.collection("users")
    fun addUser(userId: String, userData: Map<String, Any>) {
        users.document(userId)
            .set(userData)
            .addOnSuccessListener { documentReference ->
                // Document successfully written
                println("DocumentSnapshot added with ID: $documentReference.id")
            }
            .addOnFailureListener { e ->
                // Handle errors here
                println("Error adding document: $e")
            }
    }

    // Function to retrieve user data from the "users" collection
    fun getUser(userId: String, callback: (Map<String, Any>?) -> Unit) {
        users.document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.data
                    callback(userData)
                } else {
                    callback(null) // User not found
                }
            }
            .addOnFailureListener { e ->
                // Handle errors here
                println("Error getting document: $e")
                callback(null)
            }
    }
}