package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.walktalk.firebaseHandler.LoginRegisterHandler


class RegisterActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var nameSurnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        usernameEditText = findViewById(R.id.UsernameTextBox)
        nameSurnameEditText = findViewById(R.id.NameSurnameTextBox)
        emailEditText = findViewById(R.id.EmailTextBoxRegister)
        passwordEditText = findViewById(R.id.PasswordTextBoxRegister)

        val registerButton: Button = findViewById(R.id.RegisterButton)
        registerButton.setOnClickListener {
            checkInputAndSendRegistration()
        }
    }
    private fun checkInputAndSendRegistration() {
        val username = usernameEditText.text.toString()
        val nameSurname = nameSurnameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (username.isEmpty() || nameSurname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all of the fields", Toast.LENGTH_SHORT).show()
            return
        }
        val nameChecker = Regex("\\b\\w+\\s\\w+\\b")
        if (!nameChecker.containsMatchIn(nameSurname)) {
            Toast.makeText(this, "Write name and surname one after the other, with a space between them", Toast.LENGTH_SHORT).show()
            return
        }
        registrationHandler(username, nameSurname, email, password)
    }

    private fun registrationHandler(username: String, nameSurname: String, email: String, password: String) {
        var nameSurnameSplitted =nameSurname.split(" ")
        val values: Map<String, Any> = mapOf(
            "name" to nameSurnameSplitted[0],
            "surname" to nameSurnameSplitted[1],
            "email" to email,
            "password" to password
        )
        LoginRegisterHandler.addUser(username,values)
        val message = "Registration successful:\nusername: $username\nName and Surname: $nameSurname\nEmail: $email\nPassword: $password"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
