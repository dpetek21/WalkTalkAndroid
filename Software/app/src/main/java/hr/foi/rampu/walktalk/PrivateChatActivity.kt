package hr.foi.rampu.walktalk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginBottom
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hr.foi.rampu.walktalk.adapteri_za_chat.MessageAdapter
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
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
    private lateinit var toolbarBackIcon: ImageView
    private lateinit var toolbarTitle: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)

        toolbarBackIcon = findViewById(R.id.imageView_toolbar_private_chat)
        toolbarTitle = findViewById(R.id.txt_toolbar_private_chat)

        sender = UserDataContainer.username
        receiver = intent.getStringExtra("receiver_username").toString()
        Log.i("receiver, private chat", receiver)

        toolbarTitle.text = receiver

        messageList = ArrayList()
        messageList.clear()
        chatRecyclerView = findViewById(R.id.recyclerViewPrivateChat)
        chatText = findViewById<EditText>(R.id.edt_private_chat_massage)
        sendButton = findViewById<ImageButton>(R.id.imageButton_send)

        supportActionBar?.title = receiver


        database = FirebaseFirestore.getInstance()
        fetchMessagesIntoAList(sender, receiver)
        fetchMessagesIntoAList(receiver, sender)
        messageAdapter = MessageAdapter(this, messageList, sender)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        toolbarBackIcon.setOnClickListener{
            this.finish()
        }


        sendButton.setOnClickListener {
            sendMessage()
            messageList.add(Message(chatText.text.toString(), sender, receiver, Timestamp.now()))
            messageAdapter.notifyDataSetChanged()
            chatText.text.clear()
        }

        chatRecyclerView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->

            if (bottom < oldBottom) {
                if(chatRecyclerView.adapter?.itemCount != 0) {
                    chatRecyclerView.smoothScrollToPosition(
                        chatRecyclerView.adapter?.itemCount?.minus(
                            1
                        ) ?: 0
                    )
                }
            }
        }

    }

    private fun sendMessage() {
        val text = chatText.text
        if (text.toString() != "") {

            val message = Message(text.toString(), sender, receiver, Timestamp.now())
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

    private fun fetchMessagesIntoAList(user1: String, user2:String){
        messageList.clear()
        val converastion1 = conversationQuery(user1, user2)
        converastion1.get().addOnSuccessListener { result->
            if(!result.isEmpty){
                result.documents[0].reference.collection("private_messages")
                    .orderBy("timestamp")
                    .get().addOnSuccessListener { documents->
                        for(document in documents){
                            val text = document["message"].toString()
                            val senderData = document["sender"].toString()
                            val receiverData = document["receiver"].toString()
                            messageList.add(Message(text, senderData, receiverData, Timestamp.now()))
                        }
                        messageAdapter.notifyDataSetChanged()
                        chatRecyclerView.scrollToPosition(messageList.size - 1)
                    }
            }
        }
    }

}