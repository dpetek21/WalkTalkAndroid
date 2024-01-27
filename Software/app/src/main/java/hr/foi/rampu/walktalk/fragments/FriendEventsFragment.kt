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
import hr.foi.rampu.walktalk.adapters.FriendEventsAdapter
import hr.foi.rampu.walktalk.database.DatabaseEvent
import hr.foi.rampu.walktalk.entities.Event
import kotlinx.coroutines.launch

class FriendEventsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private suspend fun getFriendEvents() {
        val friendEventsList : List<Event> = DatabaseEvent.getFriendEvents()
        recyclerView = requireView().findViewById(R.id.rv_friend_events)
        recyclerView.adapter = FriendEventsAdapter(friendEventsList)
        recyclerView.layoutManager = LinearLayoutManager(requireView().context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            getFriendEvents()
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            getFriendEvents()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_going_events, container, false)
    }
}