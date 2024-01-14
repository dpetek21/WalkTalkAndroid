package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.adapteri_za_chat.GroupChatAdapter
import hr.foi.rampu.walktalk.adapteri_za_chat.MessageAdapter
import hr.foi.rampu.walktalk.database.GroupChatDAO
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.klase_za_chat.Message
import kotlinx.coroutines.launch

class GroupChatActivity : AppCompatActivity() {

    private lateinit var groupChatText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var groupChatRecyclerView: RecyclerView
    private lateinit var receiver: String
    private lateinit var sender: String
    private lateinit var messageList: ArrayList<Message>
    private lateinit var groupChatAdapter: GroupChatAdapter
    private lateinit var groupChatDAO: GroupChatDAO
    private lateinit var toolbarBackIcon: ImageView
    private lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)

        toolbarBackIcon = findViewById(R.id.imageView_toolbar_private_chat)
        toolbarTitle = findViewById(R.id.txt_toolbar_private_chat)

        sender = UserDataContainer.username
        receiver = intent.getStringExtra("group_name").toString()
        groupChatDAO = GroupChatDAO(receiver)
        groupChatText = findViewById(R.id.edt_group_chat_massage)
        sendButton = findViewById(R.id.groupChatImageButton_send)
        groupChatRecyclerView = findViewById(R.id.recyclerViewGroupChat)
        messageList = ArrayList<Message>()
        messageList.clear()

        toolbarTitle.text = receiver
        toolbarBackIcon.setOnClickListener{
            this.finish()
        }

        lifecycleScope.launch {
            messageList = groupChatDAO.fetchMessagesIntoAList()
            groupChatAdapter = GroupChatAdapter(this@GroupChatActivity, messageList, sender)
            groupChatRecyclerView.layoutManager = LinearLayoutManager(this@GroupChatActivity)
            groupChatRecyclerView.adapter = groupChatAdapter

            if(groupChatRecyclerView.adapter?.itemCount != 0) {
                groupChatRecyclerView.smoothScrollToPosition(groupChatAdapter.itemCount - 1)
            }

            groupChatRecyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
                if (bottom < oldBottom) {
                    if(groupChatRecyclerView.adapter?.itemCount != 0) {
                        groupChatRecyclerView.smoothScrollToPosition(
                            groupChatRecyclerView.adapter?.itemCount?.minus(
                                1
                            ) ?: 0
                        )
                    }
                }
            }
        }


        sendButton.setOnClickListener {
            lifecycleScope.launch {
                if(groupChatText.text.isNotEmpty()){
                    groupChatDAO.saveMessage(groupChatText.text.toString())
                    messageList.add(Message(groupChatText.text.toString(), sender, receiver, Timestamp.now()))
                    groupChatAdapter.notifyDataSetChanged()
                    groupChatText.text.clear()
                    groupChatRecyclerView.smoothScrollToPosition(groupChatAdapter.itemCount - 1)
                }
            }
        }

    }

}