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
import hr.foi.rampu.walktalk.adapteri_za_chat.GroupChatAdapter
import hr.foi.rampu.walktalk.adapteri_za_chat.MessageAdapter
import hr.foi.rampu.walktalk.database.GroupChatDAO
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
    private lateinit var toolbarBackIcon: ImageView
    private lateinit var toolbarTitle: TextView
    private lateinit var groupChatDAO: GroupChatDAO


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)

        sender = UserDataContainer.username
        receiver = intent.getStringExtra("receiver_username").toString()
        groupChatDAO = GroupChatDAO(receiver)

        chatRecyclerView = findViewById(R.id.recyclerViewPrivateChat)
        chatText = findViewById<EditText>(R.id.edt_private_chat_massage)
        sendButton = findViewById<ImageButton>(R.id.imageButton_send)

        toolbarBackIcon = findViewById(R.id.imageView_toolbar_private_chat)
        toolbarTitle = findViewById(R.id.txt_toolbar_private_chat)
        toolbarTitle.text = receiver
        toolbarBackIcon.setOnClickListener{
            this.finish()
        }
        supportActionBar?.title = receiver

        messageList = ArrayList<Message>()
        messageList.clear()

        lifecycleScope.launch {
            messageList = groupChatDAO.fetchMessagesIntoAList()
            messageAdapter = MessageAdapter(this@PrivateChatActivity, messageList, sender)
            chatRecyclerView.layoutManager = LinearLayoutManager(this@PrivateChatActivity)
            chatRecyclerView.adapter = messageAdapter

            if(chatRecyclerView.adapter?.itemCount != 0) {
                chatRecyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
            }

            chatRecyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
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


        sendButton.setOnClickListener {
            lifecycleScope.launch {
                if(chatText.text.isNotEmpty()){
                    groupChatDAO.saveMessage(chatText.text.toString())
                    messageList.add(Message(chatText.text.toString(), sender, receiver, Timestamp.now()))
                    messageAdapter.notifyDataSetChanged()
                    chatText.text.clear()
                    chatRecyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
                }
            }
        }



    }

}