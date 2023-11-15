package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hr.foi.rampu.walktalk.adapteri_za_chat.MessageAdapter
import hr.foi.rampu.walktalk.klase_za_chat.Message
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PrivateChatActivity : AppCompatActivity() {

    private lateinit var chatText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var sender: String
    private lateinit var receiver: String
    private lateinit var database: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)

        sender = "Franko"
        receiver = "Marko"
        messageList = ArrayList()
        chatRecyclerView = findViewById(R.id.recyclerViewPrivateChat)
        chatText = findViewById<EditText>(R.id.edt_private_chat_massage)
        sendButton = findViewById<ImageButton>(R.id.imageButton_send)
        messageAdapter = MessageAdapter(this, messageList, sender)
        database = FirebaseFirestore.getInstance()

        sendButton.setOnClickListener {
            sendMessage()
            chatText.text.clear()
        }

    }

    private fun sendMessage() {
        val text = chatText.text
        if (text.toString() != "") {

            val message = Message(text.toString(), sender, receiver)
            val users = hashMapOf(
                "Users" to listOf(message.sender, message.receiver)
            )

            val conversationExists1 = conversationQuery(message.sender, message.receiver)
            val conversationExist2 = conversationQuery(message.receiver, message.sender)

            lifecycleScope.launch {
                if(!saveMessage(conversationExists1, message)){
                    if(!saveMessage(conversationExist2, message))
                        saveNewMessage(users, message)
                }
            }
        }
    }

    private fun conversationQuery(user1: String?, user2: String?): Query {

        return database.collection("messages")
            .whereEqualTo("Users", listOf(user1, user2))
    }

    private suspend fun saveMessage(conversation: Query, message: Message): Boolean {
        var provjera: Boolean = false
        val users = hashMapOf(
            "Users" to listOf(message.sender, message.receiver)
        )
        conversation.get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    provjera = true
                    val conversationDocument = result.documents[0].reference
                    conversationDocument.collection("private_messages")
                        .document(UUID.randomUUID().toString())
                        .set(message)
                    Log.i("SpremiPoruku", "SaveMessage unutar funkcije je uspio")
                }
            }.await()

        return provjera;
    }

    private fun saveNewMessage(users: HashMap<String, List<String?>>, message: Message) {
        val messageChannel =
            database.collection("messages").document(UUID.randomUUID().toString())
        messageChannel.set(users).addOnSuccessListener {
            messageChannel.collection("private_messages")
                .document(UUID.randomUUID().toString())
                .set(message)
        }
    }
}