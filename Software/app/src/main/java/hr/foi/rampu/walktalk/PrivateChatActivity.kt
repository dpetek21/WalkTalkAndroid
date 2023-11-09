package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import hr.foi.rampu.walktalk.klase_za_chat.Message

class PrivateChatActivity : AppCompatActivity() {

    private lateinit var chatText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sender: String
    private lateinit var receiver: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)

        chatText = findViewById<EditText>(R.id.edt_private_chat_massage)
        databaseReference = FirebaseDatabase.getInstance("https://rampu---walk-talk-default-rtdb.europe-west1.firebasedatabase.app/").reference
        sendButton = findViewById<ImageButton>(R.id.imageButton_send)


        sendButton.setOnClickListener{
            sendMessage();
        }

    }

    private fun sendMessage(){
        val text = chatText.text
        if(text.toString() != ""){
            val message = Message(text.toString(), "1", "2")
            val messageRef = databaseReference.child("messages").push().setValue(message)
            text.clear()
        }
    }



}