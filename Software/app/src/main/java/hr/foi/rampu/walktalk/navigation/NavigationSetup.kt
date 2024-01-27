package hr.foi.rampu.walktalk.navigation

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import hr.foi.rampu.walktalk.AttentingEventsActivity
import hr.foi.rampu.walktalk.ChatsListActivity
import hr.foi.rampu.walktalk.EventsActivity
import hr.foi.rampu.walktalk.FriendsActivity
import hr.foi.rampu.walktalk.LeaderboardActivity
import hr.foi.rampu.walktalk.NotificationsActivity
import hr.foi.rampu.walktalk.PedometerActivity
import hr.foi.rampu.walktalk.ProfileActivity
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.RoutesActivity
import hr.foi.rampu.walktalk.entities.LeaderboardItem
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer

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

                activity.getString(R.string.friends) -> activityToLoad =
                    FriendsActivity::class.java

                activity.getString(R.string.attending_events) -> activityToLoad =
                    AttentingEventsActivity::class.java

                activity.getString(R.string.notifications) -> activityToLoad =
                    NotificationsActivity::class.java

                activity.getString(R.string.messages) -> activityToLoad =
                    ChatsListActivity::class.java

                activity.getString(R.string.pedometer) -> activityToLoad =
                    PedometerActivity::class.java

                activity.getString(R.string.leaderboard) -> activityToLoad =
                    LeaderboardActivity::class.java
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
        var navProfilePic: ImageView = navView.getHeaderView(0).findViewById(R.id.nav_profile_picture)
        if(UserDataContainer.profilePicUri!=null){
            Glide.with(activity)
                .load(UserDataContainer.profilePicUri)
                .circleCrop()
                .into(navProfilePic)
        }
        navProfilePic.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivity(intent)
        }

    }
}