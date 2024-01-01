package com.example.nt118_project.Fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nt118_project.Model.BusTicket
import com.example.nt118_project.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.checkerframework.checker.index.qual.GTENegativeOne
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class BookBusTicketsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var DepartureDaytV:TextView
    private lateinit var ReturnDaytV:TextView
    private lateinit var SpinnerSeat: Spinner
    private lateinit var SpinnerDestination: Spinner
    private lateinit var SpinnerStartingPoint: Spinner
    private lateinit var SearchButton: Button
    private lateinit var RoundTripCheckBox: CheckBox
    private lateinit var iVReturnDay: ImageView
    private lateinit var tVReturnDay: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_bus_tickets)

        val ReturnBtn = findViewById<ImageView>(R.id.iVBack)
        DepartureDaytV = findViewById<TextView>(R.id.DepartureDaySpinner)
        ReturnDaytV = findViewById<TextView>(R.id.ReturnDaySpinner)
        SpinnerSeat = findViewById<Spinner>(R.id.SeatSpinner)
        SpinnerDestination = findViewById<Spinner>(R.id.ToSpinner)
        SpinnerStartingPoint = findViewById<Spinner>(R.id.FromSpinner)
        SearchButton = findViewById<Button>(R.id.SearchBtn)
        RoundTripCheckBox = findViewById<CheckBox>(R.id.cBox)
        iVReturnDay = findViewById<ImageView>(R.id.iVReturnDay)
        tVReturnDay = findViewById<TextView>(R.id.tVReturnDay)

        ReturnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }

        val c: Calendar = Calendar.getInstance()
        val datepicker_depart = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(c, DepartureDaytV)
        }
        val datepicker_return = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(c, ReturnDaytV)
        }
        DepartureDaytV.setOnClickListener {
            var DatePickerDialog= DatePickerDialog(this, datepicker_depart, c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            DatePickerDialog.datePicker.minDate = System.currentTimeMillis()
            DatePickerDialog.show()
        }
        ReturnDaytV.setOnClickListener {
            var DatePickerDialog = DatePickerDialog(this, datepicker_return, c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            DatePickerDialog.datePicker.minDate = System.currentTimeMillis()
            DatePickerDialog.show()
        }

        val SeatSpinnerData: ArrayList<Any?> = ArrayList()
        SeatSpinnerData.add("1 ghế ngồi")
        SeatSpinnerData.add("2 ghế ngồi")
        SeatSpinnerData.add("3 ghế ngồi")
        SeatSpinnerData.add("4 ghế ngồi")
        val adapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item,SeatSpinnerData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerSeat.onItemSelectedListener = this
        SpinnerSeat.setAdapter(adapter)

        //val dtb = Firebase.firestore
        var DestinationSpinnerData: ArrayList<Any?> = ArrayList()
        var StartingPointSpinnerData: ArrayList<Any?> = ArrayList()
//        dtb.collection("Bus").get().addOnSuccessListener { documents ->
//            Log.d("DesData1", DestinationSpinnerData.size.toString())
//            for( document in documents)
//            {
//                val busticket= document.toObject(BusTicket::class.java)
//                if (busticket.NumSeat.toDouble() > 0)
//                {
//                    DestinationSpinnerData.add(busticket.From)
//                    StartingPointSpinnerData.add(busticket.To)
//                }
//
//            }
//        }
//            .addOnFailureListener{exception ->
//            Log.w("Error getting documents: ", exception)
//        }
        StartingPointSpinnerData.add("TP Hồ Chí Minh")
        StartingPointSpinnerData.add("Đà Nẵng")
        StartingPointSpinnerData.add("TP. Hà Nội")
        StartingPointSpinnerData.add("Khánh Hòa")
        StartingPointSpinnerData.add("Lâm Đồng")
        StartingPointSpinnerData.add("TP. Bà Rịa - Vũng Tàu")
        StartingPointSpinnerData.add("An Giang")

        DestinationSpinnerData.add("TP Hồ Chí Minh")
        DestinationSpinnerData.add("Đà Nẵng")
        DestinationSpinnerData.add("TP. Hà Nội")
        DestinationSpinnerData.add("TP. Bà Rịa - Vũng Tàu")
        DestinationSpinnerData.add("An Giang")
        DestinationSpinnerData.add("Khánh Hòa")
        DestinationSpinnerData.add("Lâm Đồng")
//        DestinationSpinnerData = DestinationSpinnerData.distinct() as ArrayList<Any?>
//        StartingPointSpinnerData = StartingPointSpinnerData.distinct() as ArrayList<Any?>
        Log.d("DesData", DestinationSpinnerData.size.toString())
        val DestinationAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,DestinationSpinnerData)
        DestinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerDestination.onItemSelectedListener = this
        SpinnerDestination.setAdapter(DestinationAdapter)

        val StartingPointAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,StartingPointSpinnerData)
        StartingPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerStartingPoint.onItemSelectedListener = this
        SpinnerStartingPoint.setAdapter(StartingPointAdapter)

        val currentDate = LocalDate.now()
        val currentDay = currentDate.dayOfMonth
        val currentMonth = currentDate.monthValue
        val currentYear = currentDate.year
        DepartureDaytV.text = "0" + currentDay.toString()+"-"+"0"+currentMonth.toString()+"-"+currentYear.toString()
        ReturnDaytV.text = "0" +currentDay.toString()+"-"+"0" +currentMonth.toString()+"-"+currentYear.toString()

        SearchButton.setOnClickListener {
            val sdf = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val dateDepart = LocalDate.parse(DepartureDaytV.text.toString(), sdf)
            val dateReturn = LocalDate.parse(ReturnDaytV.text.toString(), sdf)
            val result = dateDepart.compareTo(dateReturn)
            if (SpinnerDestination.getSelectedItem().toString() == SpinnerStartingPoint.getSelectedItem().toString()) {
                Toast.makeText(this, "Vui lòng chọn điểm xuất phát và điểm đến khác nhau", Toast.LENGTH_LONG).show()
            } else if (result > 0 && RoundTripCheckBox.isChecked == true) {
                Toast.makeText(this, "Vui lòng chọn ngày khởi hành nhỏ hơn hoặc bằng ngày về", Toast.LENGTH_LONG).show()
            }
            else
            {
                val intent = Intent(this@BookBusTicketsActivity, SelectBusTicketsActivity::class.java)
                intent.putExtra("Starting Point", SpinnerStartingPoint.getSelectedItem().toString());
                intent.putExtra("Destination Point", SpinnerDestination.getSelectedItem().toString());
                intent.putExtra("DepartTime", DepartureDaytV.text.toString());
                intent.putExtra("ReturnTime", ReturnDaytV.text.toString());
                intent.putExtra("Seat", SpinnerSeat.getSelectedItem().toString());
                intent.putExtra("RoundTrip", RoundTripCheckBox.isChecked());
                val LAUNCH_SECOND_ACTIVITY:Int = 1
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
            }
        }

        RoundTripCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                iVReturnDay.setVisibility(View.VISIBLE)
                tVReturnDay.setVisibility(View.VISIBLE)
                ReturnDaytV.setVisibility(View.VISIBLE)
            } else {
                iVReturnDay.setVisibility(View.GONE)
                tVReturnDay.setVisibility(View.GONE)
                ReturnDaytV.setVisibility(View.GONE)

            }
        }
    }

    private fun updateLable(c: Calendar, tV:TextView) {

        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tV.setText(sdf.format(c.time))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("Selected","Select Seat")
        (view as TextView).setTextColor(Color.WHITE)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("Selected","Not select seat")
    }
}
