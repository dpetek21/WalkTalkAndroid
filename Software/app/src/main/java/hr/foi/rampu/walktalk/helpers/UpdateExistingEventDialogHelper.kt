package hr.foi.rampu.walktalk.helpers

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.database.DatabaseEvent
import hr.foi.rampu.walktalk.entities.Route
import java.text.SimpleDateFormat
import java.util.Locale

class UpdateExistingEventDialogHelper(private val view: View) {
    private val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
    private val spinnerPace = view.findViewById<Spinner>(R.id.spn_pace)
    private val etEventName = view.findViewById<EditText>(R.id.et_new_event_name)
    private val spinnerRoute = view.findViewById<Spinner>(R.id.spn_route)
    private val dateSelection = view.findViewById<EditText>(R.id.et_event_date)
    private val radioGroup  = view.findViewById<RadioGroup>(R.id.rgrpVisibility)
    private val txtVisibility = view.findViewById<TextView>(R.id.txt_visibility)

    fun populateSpinnerPace(paces: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            paces)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPace.adapter = spinnerAdapter

        try {
            val currentPaceIndex = paces.indexOf(DatabaseEvent.event!!.pace)

            spinnerPace.setSelection(currentPaceIndex)
        }
        catch (error : Exception) {
            Log.e("PACE_INDEX_ERROR", error.message.toString())
            return
        }
    }
    fun populateSpinnerRoute(routes: List<Route>) {
        val spinnerAdapterRoute = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            routes
        )
        spinnerAdapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRoute.adapter = spinnerAdapterRoute

        try {
            val currentRouteIndex = routes.indexOf(DatabaseEvent.event!!.route)
            spinnerRoute.setSelection(currentRouteIndex)
        }
        catch (error : Exception) {
            Log.e("ROUTE_INDEX_ERROR", error.message.toString())
            return
        }
    }


    init {
        etEventName.setText(DatabaseEvent.event!!.name)
        dateSelection.setText(sdfDate.format(DatabaseEvent.event!!.date!!))
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

    fun hideVisibility() {
        radioGroup.visibility = GONE
        txtVisibility.visibility  = GONE
    }
}