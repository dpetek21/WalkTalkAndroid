package hr.foi.rampu.walktalk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        NavigationSetup.SetupNavigationDrawer(this)
    }
}