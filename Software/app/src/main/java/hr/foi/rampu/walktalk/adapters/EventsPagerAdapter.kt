package hr.foi.rampu.walktalk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.foi.rampu.walktalk.fragments.ExploreEventsFragment
import hr.foi.rampu.walktalk.fragments.FriendEventsFragment

class EventsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    val fragments = listOf<Fragment>(ExploreEventsFragment(), FriendEventsFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}