package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
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
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var groupChatDAO: GroupChatDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)

        receiver = "TestChat"
        groupChatDAO = GroupChatDAO(receiver)
        groupChatText = findViewById(R.id.edt_group_chat_massage)
        sendButton = findViewById(R.id.groupChatImageButton_send)
        groupChatRecyclerView = findViewById(R.id.recyclerViewGroupChat)
        messageList = ArrayList<Message>()
        messageList.clear()


        sendButton.setOnClickListener {
            lifecycleScope.launch {
                if(groupChatText.text.isNotEmpty()){
                    groupChatDAO.saveMessage(groupChatText.text.toString())
                }
            }
        }

    }


}