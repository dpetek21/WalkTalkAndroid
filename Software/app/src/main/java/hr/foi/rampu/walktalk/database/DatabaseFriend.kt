package hr.foi.rampu.walktalk.database

import android.util.Log

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import hr.foi.rampu.walktalk.entities.Event
import hr.foi.rampu.walktalk.entities.Friend
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

object DatabaseFriend {
    var username: String = UserDataContainer.username
    suspend fun getFriendsOfUser(): MutableList<Friend> = coroutineScope {
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
                        friends.add(Friend(databaseUsername))
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

    suspend fun getPendingFriendRequests(): MutableList<Friend> = coroutineScope {
        val database = Firebase.firestore
        val pendingFriendRequests : MutableList<Friend> = mutableListOf()
        val userCollection = database.collection("users")

        val loggedUserDocument = userCollection.document(username)

        loggedUserDocument.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val pendingFriendsDocuments = document.get("pending_friend_requests") as? List<DocumentReference>
                    pendingFriendsDocuments?.forEach {
                        Log.d("INFO", it.path)
                        val databaseUsername = it.path.split("/")[1]
                        Log.d("INFO", databaseUsername)
                        pendingFriendRequests.add(Friend(databaseUsername))
                    }

                    Log.d("INFO", "$pendingFriendRequests")
                } else {
                    Log.d("INFO", "No such document")
                }


            }

            .addOnFailureListener { exception ->
                Log.d("INFO", "get failed with ", exception)
            }
            .await()
        pendingFriendRequests
    }

     fun removeFriend(friendToRemove : Friend) {
         val friendUsername = friendToRemove.username
         val database = Firebase.firestore
         val userCollection = database.collection("users")
         val documentOfLoggedInUser = userCollection.document(username)
         val friendDocumentReference = userCollection.document(friendUsername)

         documentOfLoggedInUser.update(
               "pending_friend_requests",
             FieldValue.arrayRemove(friendDocumentReference)
         ).addOnSuccessListener {
             Log.i("SUCCESSDELETEFRIENDFROMFIRESTORE", "Friend $friendUsername deleted successfully from Firestore!")
         }
             .addOnFailureListener {
                 Log.e("FAILDELETEFRIENDFROMFIRESTORE", it.toString())
             }
     }

    fun addNewFriend(friendToAdd : Friend)
    {
        val friendUsername = friendToAdd.username
        val database = Firebase.firestore
        val userCollection = database.collection("users")
        val documentOfLoggedInUser = userCollection.document(username)
        val friendDocumentReference = userCollection.document(friendUsername)

        documentOfLoggedInUser.update(
            "friends",
            FieldValue.arrayUnion(friendDocumentReference)
        ).addOnSuccessListener {
            Log.i("SUCCESSADDFRIENDTOFIRESTORE", "Friend $friendUsername add successfully to Firestore!")
        }
            .addOnFailureListener {
                Log.e("FAILADDFRIENDTOFIRESTORE", it.toString())
            }

        friendDocumentReference.update(
            "friends",
            FieldValue.arrayUnion(documentOfLoggedInUser)
        ).addOnSuccessListener {
            Log.i("SUCCESSADDFRIENDTOFIRESTORE", "Friend $username add successfully to Firestore!")
        }
            .addOnFailureListener {
                Log.e("FAILADDFRIENDTOFIRESTORE", it.toString())
            }
    }

    fun addNewEvent(newEvent: Event) {
        val sdf = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
        val event = hashMapOf(
            "name" to newEvent.name,
            "numberOfKilometers" to newEvent.numberOfKilometers,
            "numberOfPeople" to newEvent.numberOfPeople,
            "pace" to newEvent.pace,
            "date" to sdf.format(newEvent.date),
            "route" to newEvent.route,
            "organizer" to username,
            "isPublic" to newEvent.isPublic
        )


        val database = Firebase.firestore
        database.collection("events").add(event).addOnSuccessListener {
            Log.i("EVENT_ADD_SUCCESS", "Event added successfully into firestore")
        }
            .addOnFailureListener {
                Log.i("EVENT_ADD_ERROR", it.toString())
            }

    }


}