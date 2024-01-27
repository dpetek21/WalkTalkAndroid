package hr.foi.rampu.walktalk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.foi.rampu.walktalk.firebaseHandler.LoginRegisterHandler
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val register : Button = findViewById(R.id.LoginRegisterButton)
        usernameEditText = findViewById(R.id.EmailTextBox)
        passwordEditText = findViewById(R.id.PasswordTextBox)
        register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val login : Button = findViewById(R.id.loginButton)
        login.setOnClickListener{
            sendLogin()
        }

    }
    private fun sendLogin() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all of the fields", Toast.LENGTH_SHORT).show()
            return
        }
        tryLogin(username, password)
    }
    private fun tryLogin(username: String, password: String) {
        val handler = LoginRegisterHandler()
        handler.checkLogin(username,password){ isSucess ->
            if(isSucess){
                val message = "Login successful!"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                UserDataContainer.username = username
                handler.getUser(UserDataContainer.username) { gottenUser ->
                    if(gottenUser != null && gottenUser!!.containsKey("profilePicUrl")){
                        val profilePicUrl = gottenUser["profilePicUrl"] as String
                        if(profilePicUrl.isNotEmpty()){
                            UserDataContainer.profilePicUri= Uri.parse(gottenUser["profilePicUrl"] as String)
                            Log.d("Pfp", "Profile Pic URL: ${UserDataContainer.profilePicUri}")
                        }
                    }
                    val intent = Intent(this, EventsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }else{
                val message = "Login unsuccessful: check your inputs"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}