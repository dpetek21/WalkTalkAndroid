package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.database.ChatsListDAO
import kotlinx.coroutines.launch


private lateinit var groupsRecyclerView: RecyclerView
private lateinit var privateChatsRecyclerView: RecyclerView
private lateinit var privateChatList: List<String>
private lateinit var groupChatList: List<String>
private lateinit var chatsListDAO: ChatsListDAO

class ChatsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats_list)

        chatsListDAO = ChatsListDAO()
        lifecycleScope.launch {
            privateChatList = chatsListDAO.fetchPrivateChatsIntoAList()
            groupChatList = chatsListDAO.fetchGroupChatsIntoAList()
            Log.i("private", privateChatList[0])
            Log.i("group", groupChatList[0])
        }

    }

}