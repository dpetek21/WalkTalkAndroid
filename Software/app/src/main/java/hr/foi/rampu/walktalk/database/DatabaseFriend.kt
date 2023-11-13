package hr.foi.rampu.walktalk.database

import android.util.Log

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import hr.foi.rampu.walktalk.entities.Friend
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

object DatabaseFriend {

    suspend fun getFriendsOfUser(username: String): MutableList<Friend> = coroutineScope {
        val database = Firebase.firestore
        val friends : MutableList<Friend> = mutableListOf()
        val userCollection = database.collection("users")

        val loggedUserDocument = userCollection.document(username)

        loggedUserDocument.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val friendsDocuments = document.get("friends") as? List<DocumentReference>
                    friendsDocuments?.forEach {
                        Log.d("INFO", it.path)
                        val databaseUsername = it.path.split("/")[1]
                        Log.d("INFO", databaseUsername)
                        friends.add(Friend(username))
                    }

                    Log.d("INFO", "$friends")
                } else {
                    Log.d("INFO", "No such document")
                }


            }

            .addOnFailureListener { exception ->
                Log.d("INFO", "get failed with ", exception)
            }
            .await()
        friends
    }
}