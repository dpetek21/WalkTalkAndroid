package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)


        backButton = findViewById(R.id.img_event_details_back_arrow)

        backButton.setOnClickListener{
            this.finish()
        }
    }


}