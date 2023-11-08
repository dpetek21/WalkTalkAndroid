package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Friend

class PendingFriendsAdapter(val friendsList : List<Friend>) : RecyclerView.Adapter<PendingFriendsAdapter.PendingFriendsViewHolder>() {
    inner class PendingFriendsViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private val username : TextView
        init {
            username = view.findViewById(R.id.txtv_username_pending_friend)
        }

        fun bind(friend: Friend)
        {
            username.text = friend.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingFriendsViewHolder {
        val friendsView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pending_friend_ist_item, parent, false)
        return PendingFriendsViewHolder(friendsView)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(holder: PendingFriendsViewHolder, position: Int) {
        holder.bind(friendsList[position])
    }
}