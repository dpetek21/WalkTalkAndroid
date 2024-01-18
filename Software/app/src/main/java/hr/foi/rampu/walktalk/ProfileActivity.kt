package hr.foi.rampu.walktalk

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.foi.rampu.walktalk.firebaseHandler.LoginRegisterHandler
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class ProfileActivity : AppCompatActivity() {
    var user:Map<String,Any>? = null
    var handler : LoginRegisterHandler =  LoginRegisterHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        handler.getUser(UserDataContainer.username) { gottenUser ->
            user = gottenUser
            if (user != null && user!!.containsKey("display")) {
                val displayName = findViewById<EditText>(R.id.txbDisplay)
                displayName.setText(user!!["display"].toString())
            }
            if (user != null && user!!.containsKey("description")) {
                val description = findViewById<EditText>(R.id.txbDescription)
                description.setText(user!!["description"].toString())
            }
            if (user != null && user!!.containsKey("details")) {
                val details = findViewById<EditText>(R.id.txbDetails)
                details.setText(user!!["details"].toString())
            }
        }
        val save : Button = findViewById(R.id.btnSaveProfile)
        save.setOnClickListener{
            saveProfile()
        }
        NavigationSetup.SetupNavigationDrawer(this)
    }

    private fun saveProfile() {
        val displayName = findViewById<EditText>(R.id.txbDisplay)
        val description = findViewById<EditText>(R.id.txbDescription)
        val details = findViewById<EditText>(R.id.txbDetails)
        val userData = mapOf(
            "display" to displayName.text.toString(),
            "description" to description.text.toString(),
            "details" to details.text.toString()
        )
        Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()
        handler.updateUser(UserDataContainer.username,userData);
    }
}