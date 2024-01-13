package hr.foi.rampu.walktalk.adapteri_za_chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.klase_za_chat.Message

class GroupChatAdapter(val context: Context, val messageList: ArrayList<Message>, val sender: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SENT_MESSAGE = 1
        const val VIEW_TYPE_RECEIVED_MESSAGE = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
                SentMessageViewHolder(view)
            }
            VIEW_TYPE_RECEIVED_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.receive_message, parent, false)
                ReceivedMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]

        when (holder.itemViewType) {
            VIEW_TYPE_SENT_MESSAGE -> {
                val sentMessageViewHolder = holder as SentMessageViewHolder
                sentMessageViewHolder.sentMessage.text = message.message
            }
            VIEW_TYPE_RECEIVED_MESSAGE -> {
                val receivedMessageViewHolder = holder as ReceivedMessageViewHolder
                receivedMessageViewHolder.senderUsername.text = message.sender
                receivedMessageViewHolder.receivedMessage.text = message.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return if (message.sender == sender) {
            VIEW_TYPE_SENT_MESSAGE
        } else {
            VIEW_TYPE_RECEIVED_MESSAGE
        }
    }

    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_massage)
    }

    class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.txt_receive_massage)
        val senderUsername = itemView.findViewById<TextView>(R.id.txt_sender_username)
    }

}