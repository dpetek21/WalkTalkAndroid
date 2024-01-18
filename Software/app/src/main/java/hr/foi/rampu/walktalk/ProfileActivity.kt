package hr.foi.rampu.walktalk

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import hr.foi.rampu.walktalk.firebaseHandler.LoginRegisterHandler
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class ProfileActivity : AppCompatActivity() {
    var user:Map<String,Any>? = null
    var handler : LoginRegisterHandler =  LoginRegisterHandler()
    var uri : Uri?=null
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
            if (UserDataContainer.profilePicUri!=null) {
                val imageView = findViewById<ImageView>(R.id.imgProfilePic)
                Glide.with(this)
                    .load(UserDataContainer.profilePicUri)
                    .into(imageView)
            }
        }
        val save : Button = findViewById(R.id.btnSaveProfile)
        save.setOnClickListener{
            saveProfile()
        }
        val image : Button = findViewById(R.id.btnChoosePicture)
        image.setOnClickListener{
            choosePicture()
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
        if(uri!=null){
            handler.uploadProfilePic(UserDataContainer.username, uri!!) { imageUrl ->
                val userData = mapOf(
                    "profilePicUrl" to imageUrl
                )
                handler.updateUser(UserDataContainer.username, userData)
                UserDataContainer.profilePicUri=Uri.parse(imageUrl)
            }
        }
    }
    private fun choosePicture(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            uri = selectedImageUri
            val imageView = findViewById<ImageView>(R.id.imgProfilePic)
            Glide.with(this)
                .load(UserDataContainer.profilePicUri)
                .circleCrop()
                .into(imageView)
        }
    }
}