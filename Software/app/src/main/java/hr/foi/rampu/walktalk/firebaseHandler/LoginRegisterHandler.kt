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
                //println("Document added with ID: $documentReference.id")
            }
            .addOnFailureListener { e ->
                //println("Error adding document: $e")
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
                    //Log.d("LoginRegisterHandler", "Pronadjen user s tim E-mailom")
                    //Log.d("LoginRegisterHandler", usermap.toString())
                    //Log.d("LoginRegisterHandler", password)
                    if(storedPassword==password){
                        isgood++
                        //Log.d("LoginRegisterHandler", "Password pase")
                        callback(true)
                    }else{
                        callback(false)
                    }
                } else {
                    //Log.d("LoginRegisterHandler", "Nema user s tim mailom")
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                //Log.d("LoginRegisterHandler", "Error dobivanja dokumenta")
                callback(false)
            }
    }
    fun checkLogin(userId: String, password: String, callback: (Boolean) -> Unit){
        var isgood=0
        //var usermap: Map<String,Any>
        users
            .document(userId)
            .get()
            .addOnSuccessListener { query ->
                if (query.exists()) {
                    val usermap = query.data as Map<String,Any>
                    val storedPassword = usermap["password"] as? String ?: ""
                    //Log.d("LoginRegisterHandler", "Pronadjen user s tim IDom")
                    //Log.d("LoginRegisterHandler", usermap.toString())
                    //Log.d("LoginRegisterHandler", password)
                    if(storedPassword==password){
                        isgood++
                        //Log.d("LoginRegisterHandler", "Password pase")
                        callback(true)
                    }else{
                        callback(false)
                    }
                } else {
                    //Log.d("LoginRegisterHandler", "Nema user s tim IDom")
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                //Log.d("LoginRegisterHandler", "Error dobivanja dokumenta")
                callback(false)
            }
    }
}