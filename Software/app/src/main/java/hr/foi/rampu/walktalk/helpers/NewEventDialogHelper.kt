package hr.foi.rampu.walktalk.helpers

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import hr.foi.rampu.walktalk.R
import java.text.SimpleDateFormat
import java.util.Locale

class NewEventDialogHelper(private val view: View) {
    private val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
    private val spinnerPace = view.findViewById<Spinner>(R.id.spn_pace)
    private val spinnerRoute = view.findViewById<Spinner>(R.id.spn_route)
    private val dateSelection = view.findViewById<EditText>(R.id.et_event_date)

    fun populateSpinnerPace(categories: List<String>) = Pace.getAllPaces()
}