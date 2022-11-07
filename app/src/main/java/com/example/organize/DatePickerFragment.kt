package com.example.organize

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val cal: Calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("MM/yyyy", Locale.US)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            AlertDialog.THEME_HOLO_DARK,
            this,
            year,
            month,
            day
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        cal.set(year, month, dayOfMonth)
        val selectedDate = formatter.format(cal.time)
        val selectedDateBundle = Bundle()
        selectedDateBundle.putString("SELECTED_DATE", selectedDate)

        setFragmentResult("REQUEST_KEY", selectedDateBundle)

    }
}