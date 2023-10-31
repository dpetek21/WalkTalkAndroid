package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toDrawable
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FriendsActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        tabLayout = findViewById(R.id.current_incoming_friendsTab)
        viewPager = findViewById(R.id.vp2Friends)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = R.string.friends.toString()
                    tab.icon = R.drawable.ic_friends.toDrawable()
                }
                1 -> {
                    tab.text = R.string.pending_friend_requests.toString()
                    tab.icon = R.drawable.ic_friend_request_pending.toDrawable()
                }
            }
        }.attach()
    }
}