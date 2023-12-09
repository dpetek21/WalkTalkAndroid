package hr.foi.rampu.walktalk.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import hr.foi.rampu.walktalk.AttentingEventsActivity
import hr.foi.rampu.walktalk.EventsActivity
import hr.foi.rampu.walktalk.FriendsActivity
import hr.foi.rampu.walktalk.NotificationsActivity
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.RoutesActivity

object NavigationSetup {
    private lateinit var navDrawerLayout : DrawerLayout
    private lateinit var navView : NavigationView

    fun SetupNavigationDrawer(activity : AppCompatActivity) {
        navDrawerLayout = activity.findViewById(R.id.nav_drawer_layout)
        navView = activity.findViewById(R.id.nav_view)
        val currentActivity = activity.localClassName

        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            var activityToLoad : Class<*>? = null
            when(menuItem.title) {
                activity.getString(R.string.my_routes) -> activityToLoad =
                    RoutesActivity::class.java

                activity.getString(R.string.my_events) -> activityToLoad =
                    EventsActivity::class.java

                activity.getString(R.string.friends) -> activityToLoad = FriendsActivity::class.java
                activity.getString(R.string.attending_events) -> activityToLoad =
                    AttentingEventsActivity::class.java

                activity.getString(R.string.notifications) -> activityToLoad =
                    NotificationsActivity::class.java
            }
            navDrawerLayout.close()
            if (currentActivity == activityToLoad?.simpleName) {
                true
            }
            else {
                val intent = Intent(activity, activityToLoad)
                activity.startActivity(intent)
                true
            }
        }

    }
}