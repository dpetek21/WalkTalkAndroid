package hr.foi.rampu.walktalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class EventsActivity : AppCompatActivity() {
    lateinit var navDrawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        navDrawerLayout = findViewById(R.id.nav_drawer_layout)
        navView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            var activityToLoad : Class<*>? = null
            when(menuItem.title)
            {
                getString(R.string.my_routes) -> activityToLoad = RoutesActivity::class.java
                getString(R.string.my_events) -> activityToLoad = EventsActivity::class.java
                getString(R.string.friends) -> activityToLoad = FriendsActivity::class.java
                getString(R.string.attending_events) -> activityToLoad = AttentingEventsActivity::class.java
                getString(R.string.notifications) -> activityToLoad = NotificationsActivity::class.java
            }
            val intent = Intent(this, activityToLoad)
            startActivity(intent)
            navDrawerLayout.close()
            true
        }
    }
}