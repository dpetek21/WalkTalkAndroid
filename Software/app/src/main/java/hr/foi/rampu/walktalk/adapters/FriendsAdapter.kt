package hr.foi.rampu.walktalk.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.PrivateChatActivity
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Friend

class FriendsAdapter(val friendsList : List<Friend>) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    inner class FriendsViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val username : TextView
        val chatButton: Button
        init {
            username = view.findViewById(R.id.txtv_username)
            chatButton = view.findViewById(R.id.btn_chat)
        }

        fun bind(friend: Friend)
        {
            username.text = friend.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val friendsView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.friend_list_item, parent, false)
        return FriendsViewHolder(friendsView)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(friendsList[position])
        holder.chatButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, PrivateChatActivity::class.java)
            intent.putExtra("receiver_username",holder.username.text)
            holder.itemView.context.startActivity(intent)
        }
    }
}