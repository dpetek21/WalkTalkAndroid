package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var eventName: TextView
    private lateinit var numberOfPeople: TextView
    private lateinit var numberOfKilometers: TextView
    private lateinit var eventDate: TextView
    private lateinit var pace: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)


        backButton = findViewById(R.id.img_event_details_back_arrow)
        eventName = findViewById(R.id.txt_event_details)
        numberOfPeople = findViewById(R.id.txtv_event_detail_people)
        numberOfKilometers = findViewById(R.id.txtv_event_detail_kilometres)
        eventDate = findViewById(R.id.txtv_event_detail_date)
        pace = findViewById(R.id.txtv_event_detail_pace)

        eventName.text = intent.getStringExtra("event_name")
        numberOfPeople.text =
            getString(R.string.number_of_people_going, intent.getStringExtra("event_people").toString())
        numberOfKilometers.text = getString(R.string.number_of_kilometers,intent.getStringExtra("event_kilometers"))
        eventDate.text = intent.getStringExtra("event_date").toString()
        pace.text = getString(R.string.pace,intent.getStringExtra("event_pace"))


        backButton.setOnClickListener{
            this.finish()
        }
    }


}