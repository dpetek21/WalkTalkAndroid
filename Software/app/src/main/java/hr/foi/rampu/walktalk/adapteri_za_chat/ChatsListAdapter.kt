package hr.foi.rampu.walktalk.adapteri_za_chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R

class ChatsListAdapter(val context: Context, val chatsList: List<String>):
    RecyclerView.Adapter<ChatsListAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatTitleTextView: TextView = itemView.findViewById(R.id.txt_chat_list_item_name)
        val dividerView: View = itemView.findViewById(R.id.dividerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chats_list_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatsList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatTitle = chatsList[position]
        holder.chatTitleTextView.text = chatTitle

        holder.dividerView.visibility = if (position == chatsList.size - 1) View.GONE else View.VISIBLE
    }

}