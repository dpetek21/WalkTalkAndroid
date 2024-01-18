package hr.foi.rampu.walktalk.firebaseHandler
//import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class LoginRegisterHandler {
    val db = FirebaseFirestore.getInstance()
    val users= db.collection("users")
    fun addUser(userId: String, userData: Map<String, Any>) {
        users.document(userId)
            .set(userData)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }
    fun updateUser(userId: String, userData: Map<String, Any>) {
        users.document(userId)
            .update(userData)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
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
                callback(null)
            }
    }
    fun checkEmailLogin(userId: String, password: String, callback: (Boolean) -> Unit){
        var isgood=0
        //var usermap: Map<String,Any>
        users
            .whereEqualTo("email", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {

                     val user = querySnapshot.documents[0]
                     val usermap = user.data as Map<String,Any>
                    val storedPassword = usermap["password"] as? String ?: ""
                    if(storedPassword==password){
                        isgood++
                        callback(true)
                    }else{
                        callback(false)
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                callback(false)
            }
    }
    fun checkLogin(userId: String, password: String, callback: (Boolean) -> Unit){
        var isgood=0
        users
            .document(userId)
            .get()
            .addOnSuccessListener { query ->
                if (query.exists()) {
                    val usermap = query.data as Map<String,Any>
                    val storedPassword = usermap["password"] as? String ?: ""
                    if(storedPassword==password){
                        isgood++
                        callback(true)
                    }else{
                        callback(false)
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                callback(false)
            }
    }
}