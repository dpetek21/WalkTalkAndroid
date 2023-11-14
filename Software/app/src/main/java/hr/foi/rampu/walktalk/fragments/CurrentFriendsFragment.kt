package hr.foi.rampu.walktalk.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.adapters.FriendsAdapter
import hr.foi.rampu.walktalk.database.DatabaseFriend
import kotlinx.coroutines.launch

class CurrentFriendsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            recyclerView = view.findViewById(R.id.rv_current_friends)
            getFriends()
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }
    }

    private suspend fun getFriends() {
        val friends = DatabaseFriend.getFriendsOfUser()
        recyclerView.adapter = FriendsAdapter(friends)
    }
}
