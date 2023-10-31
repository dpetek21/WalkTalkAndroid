package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import hr.foi.rampu.walktalk.adapters.FriendsPagerAdapter

class FriendsActivity : AppCompatActivity() {

    private lateinit var tab : TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        tab = findViewById(R.id.current_incoming_friendsTab)
        viewPager = findViewById(R.id.vpFriends)


    }
}