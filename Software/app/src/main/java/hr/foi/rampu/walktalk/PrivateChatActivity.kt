package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.klase_za_chat.Message
import java.util.UUID

class PrivateChatActivity : AppCompatActivity() {

    private lateinit var chatText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var sender: String
    private lateinit var receiver: String
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)

        chatText = findViewById<EditText>(R.id.edt_private_chat_massage)
        sendButton = findViewById<ImageButton>(R.id.imageButton_send)
        database = FirebaseFirestore.getInstance()

        sendButton.setOnClickListener{
            sendMessage()
            chatText.text.clear()
        }

    }

    private fun sendMessage(){
        val text = chatText.text
        if(text.toString() != ""){

            val message = Message(text.toString(), "1", "2")
            val users = hashMapOf(
                "User1" to message.sender,
                "User2" to message.receiver
            )
            val messageChannel = database.collection("messages").document(UUID.randomUUID().toString())
            messageChannel.set(users).addOnSuccessListener {
                messageChannel.collection("private_messages")
                    .document(UUID.randomUUID().toString())
                    .set(message)
            }

        }
    }


}