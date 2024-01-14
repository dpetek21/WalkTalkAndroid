package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.adapteri_za_chat.ChatsListAdapter
import hr.foi.rampu.walktalk.database.ChatsListDAO
import kotlinx.coroutines.launch


private lateinit var groupsRecyclerView: RecyclerView
private lateinit var privateChatsRecyclerView: RecyclerView
private lateinit var privateChatList: List<String>
private lateinit var groupChatList: List<String>
private lateinit var chatsListDAO: ChatsListDAO
private lateinit var privateChatsListAdapter: ChatsListAdapter
private lateinit var groupChatsListAdapter: ChatsListAdapter

class ChatsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats_list)

        privateChatsRecyclerView = findViewById(R.id.privateChatsRecyclerView)
        groupsRecyclerView = findViewById(R.id.groupChatsRecyclerView)

        chatsListDAO = ChatsListDAO()
        lifecycleScope.launch {
            privateChatList = chatsListDAO.fetchPrivateChatsIntoAList()
            groupChatList = chatsListDAO.fetchGroupChatsIntoAList()

            privateChatsListAdapter = ChatsListAdapter(this@ChatsListActivity, privateChatList)
            groupChatsListAdapter = ChatsListAdapter(this@ChatsListActivity, groupChatList)

            privateChatsRecyclerView.layoutManager = LinearLayoutManager(this@ChatsListActivity)
            privateChatsRecyclerView.adapter = privateChatsListAdapter

            groupsRecyclerView.layoutManager = LinearLayoutManager(this@ChatsListActivity)
            groupsRecyclerView.adapter = groupChatsListAdapter
        }

    }

}