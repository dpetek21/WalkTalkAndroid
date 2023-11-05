package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.walktalk.adapters.FriendsPagerAdapter

class FriendsActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        tabLayout = findViewById(R.id.current_incoming_friendsTab)
        viewPager = findViewById(R.id.vp2Friends)

        viewPager.adapter = FriendsPagerAdapter(supportFragmentManager,lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.friends)
                    tab.icon = AppCompatResources.getDrawable(baseContext,R.drawable.ic_friends)
                }
                1 -> {
                    tab.text = getString(R.string.pending_friend_requests)
                    tab.icon = AppCompatResources.getDrawable(baseContext,R.drawable.ic_friend_request_pending)
                }
            }
        }.attach()
    }
}