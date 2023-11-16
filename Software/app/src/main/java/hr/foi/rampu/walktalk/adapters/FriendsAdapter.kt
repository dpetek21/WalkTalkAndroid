package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Friend

class FriendsAdapter(val friendsList : List<Friend>) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    inner class FriendsViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private val username : TextView
        init {
            username = view.findViewById(R.id.txtv_username)
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
    }
}