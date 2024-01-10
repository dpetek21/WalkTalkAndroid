package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer

class GroupChatDAO(private val receiver: String) {
    private val database = FirebaseFirestore.getInstance()
    private val sender = UserDataContainer.username

    fun saveMessage(messageText: String) {

    }

    private fun messagesExists() {
        val usersCollection = database.collection("users")
        val userDocument = usersCollection.document(sender)

        chatsCollectionExists()

        val chats = userDocument.collection("chats")
        val receiverDocument = chats.document(receiver)

        receiverDocument.get().addOnCompleteListener { receiverDocTask ->
            if (receiverDocTask.isSuccessful) {
                val receiverDocSnapshot = receiverDocTask.result
                if (receiverDocSnapshot != null && receiverDocSnapshot.exists()) {
                    Log.i("Firestore", "Receiver document exists in Chats collection")
                } else {
                    Log.i("Firestore", "Receiver document does not exist in Chats collection")
                }
            } else {
                Log.e("Firestore", "Error fetching Receiver document: ${receiverDocTask.exception}")
            }
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