package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import kotlinx.coroutines.tasks.await

class ChatsListDAO {
    private val database = FirebaseFirestore.getInstance()
    private val loggedInUser = UserDataContainer.username

    suspend fun fetchPrivateChatsIntoAList(): List<String> {
        val chats = ArrayList<String>()
        try {
            val usersCollection = database.collection("users")
            val userDocument = usersCollection.document(loggedInUser)
            val chatsCollection = userDocument.collection("chats")
            val privateChatsQuery = chatsCollection.whereEqualTo("group", false)
            val documents = privateChatsQuery.get().await()
            for (document in documents) {
                val documentName = document.id
                chats.add(documentName)
            }
        } catch (exception: Exception) {
            Log.e("TAG", "Error getting private chats", exception)
        }
        return chats
    }

    suspend fun fetchGroupChatsIntoAList(): List<String> {
        val chats = ArrayList<String>()
        try {
            val usersCollection = database.collection("users")
            val userDocument = usersCollection.document(loggedInUser)
            val chatsCollection = userDocument.collection("chats")
            val groupChatsQuery = chatsCollection.whereEqualTo("group", true)
            val documents = groupChatsQuery.get().await()
            for (document in documents) {
                val documentName = document.id
                chats.add(documentName)
            }
        } catch (exception: Exception) {
            Log.e("TAG", "Error getting group chats", exception)
        }
        return chats
    }
}