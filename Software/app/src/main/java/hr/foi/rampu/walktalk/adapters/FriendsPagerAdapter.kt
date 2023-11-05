package hr.foi.rampu.walktalk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.foi.rampu.walktalk.fragments.CurrentFriendsFragment
import hr.foi.rampu.walktalk.fragments.PendingFriendsFragment


class FriendsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    val fragments = listOf<Fragment>(CurrentFriendsFragment(),PendingFriendsFragment())
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}


