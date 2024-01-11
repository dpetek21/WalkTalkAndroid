package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.klase_za_chat.Message
import kotlinx.coroutines.tasks.await
import java.util.UUID

class GroupChatDAO(private val receiver: String) {
    private val database = FirebaseFirestore.getInstance()
    private val sender = UserDataContainer.username

    suspend fun saveMessage(messageText: String) {
        if (messagesExists()) {
            val chatDocument = referenceToChat()
            if (chatDocument != null) {
                saveMessageInCollection(chatDocument, messageText)
            }
        } else {
            Log.i("saveMessage", "Messages between users don't exist")
        }
    }

    private suspend fun saveMessageInCollection(chatDocument: DocumentReference, messageText: String) {
        val messageId = UUID.randomUUID().toString()
        val message = Message(messageText, sender, receiver, Timestamp.now())
        val messagesCollection = chatDocument.collection("private_messages")
        val newMessageDocument = messagesCollection.document(messageId)
        try {
            newMessageDocument.set(message).await()
            Log.i("saveMessage", "Message saved successfully")
        } catch (exception: Exception) {
            Log.e("saveMessage", "Error saving message: $exception")
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

    private suspend fun referenceToChat(): DocumentReference? {
        val usersCollection = database.collection("users")
        val userDocument = usersCollection.document(sender)
        val chats = userDocument.collection("chats")
        val receiverDocument = chats.document(receiver)

        return try {
            val receiverDocSnapshot = receiverDocument.get().await()
            if (receiverDocSnapshot.exists() && receiverDocSnapshot.contains("referenceToChat")) {
                val referencedDocumentRef = receiverDocSnapshot.getDocumentReference("referenceToChat")
                referencedDocumentRef
            } else {
                Log.i("referenceToChat", "It doesn't contain referenceToChat")
                null
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching Receiver document: $e")
            null
        }
    }


}