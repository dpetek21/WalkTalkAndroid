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
            createChat(messageText)
        }
    }

    /**
     * Sprema poruku u private messages kolekciju unutar specifičnog chat-a. Generira UUID, Message
     * objekt i doda ga u kolekciju. Baza: messages(kolekcija) -> UUID(dokument, chat dvaju korisnika) ->
     * private_messages(kolekcija) -> UUID(dokument, poruka) -> message, sender, receiver (atributi)
     *
     * @param chatDocument referenca na dokument gdje će se poruke spremati u private messages kolekciju.
     * @param messageText kontekst poruke.
     */
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

    /**
     * Provjerava postoje li poruke između prijavljenog korisnika i osobe ili grupe
     * kojoj se šalju poruke. Verificira postoji li chats kolekcija, onda provjerava posoji li
     * dokument primatelja unutar kolekcije chats prijavljenog korisnika u Firestore bazi.
     * Baza: users(kolekcija) -> username prijavljenog korisnika(dokument) -> chats(kolekcija) ->
     * username primatelja ili ime grupe(dokument)
     *
     * @return True ako poruke postoji, false ako ne postoji.
     */
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

    /**
     * Provjerava postoji li chat kolekcija kod prijavljenog korisnika u Firestore bazi.
     * Ako ne postoji kreira je.
     * Baza: users(kolekcija) -> username(dokument) -> chats(kolekcija)
     */
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

    /**
     * Kreira chats kolekciju kod korisnika u Firestore bazi.
     * Baza: users(kolekcija) -> username(dokument) -> chats(kolekcija)
     *
     * @param userDocument Dokument korisnika u kolekciji users.
     */
    private fun createChatsCollection(userDocument: DocumentReference) {
        userDocument.collection("chats").document("placeholder").set(mapOf("dummy" to "data"))
            .addOnSuccessListener {
                Log.i("Firestore", "Chats collection created successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error creating Chats collection: $exception")
            }
    }

    /**
     * Vraća referencu na dokument chat između pošiljatelja i primatelja, referencu uzima iz chats kolekcije
     * prijavljenog korisnika ako se unutra nalazi dokument primatelja i unutar dokumenta referenca.
     * Baza: messages(kolekcija) -> UUID(dokument, chat dvaju korisnika).
     *
     * @return vraća referencu na chat dokument ako postoji, inaće vraća null.
     */
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

    private suspend fun createChat(messageText: String){
        val messagesCollection = database.collection("messages")
        val chatId = UUID.randomUUID().toString()
        val usersMap = mapOf(
            "sender" to sender,
            "receiver" to receiver
        )
        val newChatDocument = messagesCollection.document(chatId)
        newChatDocument.set(usersMap).addOnSuccessListener {
            Log.i("createChat", "Chat created successfully")
        }.addOnFailureListener { exception ->
            Log.e("createChat", "Error creating chat: $exception")
        }
        saveMessageInCollection(newChatDocument, messageText)
    }


}