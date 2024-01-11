package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import kotlinx.coroutines.tasks.await

class GroupChatDAO(private val receiver: String) {
    private val database = FirebaseFirestore.getInstance()
    private val sender = UserDataContainer.username

    suspend fun saveMessage(messageText: String) {
        Log.i("saveMessage", messagesExists().toString())
        if(messagesExists()){
            Log.i("saveMessage", "Messages between users exist")
        }else{
            Log.i("saveMessage", "Messages between users doesnt exist")
        }
    }

    private suspend fun messagesExists(): Boolean {
        val usersCollection = database.collection("users")
        val userDocument = usersCollection.document(sender)

        chatsCollectionExists()

        val chats = userDocument.collection("chats")
        val receiverDocument = chats.document(receiver)

        return try {
            val receiverDocSnapshot = receiverDocument.get().await()
            receiverDocSnapshot.exists()
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching Receiver document: $e")
            false
        }
    }

    private fun chatsCollectionExists(){
        val usersCollection = database.collection("users")
        val userDocument = usersCollection.document(sender)
        val chats = userDocument.collection("chats")
        chats.get().addOnCompleteListener { chatsTask ->
            if (chatsTask.isSuccessful) {
                val chatsSnapshot = chatsTask.result
                if (chatsSnapshot == null || chatsSnapshot.isEmpty) {
                    createChatsCollection(userDocument)
                }
            } else {
                Log.e("Firestore", "Error fetching Chats collection: ${chatsTask.exception}")
            }
        }
    }

    private fun createChatsCollection(userDocument: DocumentReference) {
        userDocument.collection("chats").document("placeholder").set(mapOf("dummy" to "data"))
            .addOnSuccessListener {
                Log.i("Firestore", "Chats collection created successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error creating Chats collection: $exception")
            }
    }

}