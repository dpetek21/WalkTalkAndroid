package hr.foi.rampu.walktalk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.fragments.CurrentFriendsFragment
import hr.foi.rampu.walktalk.fragments.PendingFriendsFragment

class FriendsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    val titles = listOf(R.string.friends,R.string.pending_friend_requests)
    override fun getItemCount(): Int {
        return titles.size;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CurrentFriendsFragment()
            else -> PendingFriendsFragment()
        }
    }
}