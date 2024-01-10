package hr.foi.rampu.walktalk.database

import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer

class GroupChatDAO {
    private val database = FirebaseFirestore.getInstance()
    private val sender = UserDataContainer.username

    fun saveMessage(messageText: String){

    }
}