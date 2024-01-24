package hr.foi.rampu.walktalk.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Route
import java.text.SimpleDateFormat
import java.util.Locale

class NewEventDialogHelper(private val view: View) {
    private val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
    private val spinnerPace = view.findViewById<Spinner>(R.id.spn_pace)
    private val spinnerRoute = view.findViewById<Spinner>(R.id.spn_route)
    private val dateSelection = view.findViewById<EditText>(R.id.et_event_date)

    fun populateSpinnerPace(paces: List<String>) {
        val spinnerAdapterPace = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            paces)
        spinnerAdapterPace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPace.adapter = spinnerAdapterPace
    }

    fun populateSpinnerRoute(routes: List<Route>) {
        val spinnerAdapterRoute = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            routes
        )
        spinnerAdapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRoute.adapter = spinnerAdapterRoute
    }

    fun activateDateListener(supportFragmentManager: FragmentManager) {
        dateSelection.setOnFocusChangeListener{view, hasFocus ->
            if (hasFocus) {
                val picker = MaterialDatePicker.Builder
                    .datePicker()
                    .setTitleText("Select date of the event:")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                picker.show(supportFragmentManager, "EVENT_DATE")
                picker.addOnPositiveButtonClickListener {
                    dateSelection.setText(sdfDate.format(it).toString())
                }
                view.clearFocus()
            }
        }
    }
}