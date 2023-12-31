package com.example.nt118_project.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nt118_project.Adapter.BusTicketAdapter
import com.example.nt118_project.Model.BusTicket
import com.example.nt118_project.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.locks.ReentrantLock

class SelectBusTickets_2Activity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var tv_search: TextView
    private lateinit var RecyclerViewBusTicket: RecyclerView
    private lateinit var tvSeat: TextView
    private lateinit var DepartureDaytV: TextView
    private lateinit var dataList:ArrayList<BusTicket>
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var progresssDialog: ProgressDialog
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_bus_tickets2)

        val myIntent = intent // this is just for example purpose
        val startingpoint: String = myIntent.getStringExtra("Starting Point").toString()
        val destinationpoint: String = myIntent.getStringExtra("Destination Point").toString()
        val ReturnBtn = findViewById<ImageView>(R.id.iVBack)
        progresssDialog = ProgressDialog(this)
        tv_search = findViewById<TextView>(R.id.tv_search)
        DepartureDaytV = findViewById<TextView>(R.id.DepartureDaySpinner)
        tvSeat = findViewById<TextView>(R.id.tVSeat)
        spinner1 = findViewById<Spinner>(R.id.SpinnerFrom)
        spinner2 = findViewById<Spinner>(R.id.SpinnerTo)
        tv_search.text = destinationpoint + " -> " + startingpoint
        DepartureDaytV.text = myIntent.getStringExtra("ReturnTime").toString()
        tvSeat.text = myIntent.getStringExtra("Seat").toString()
        val max_require = myIntent.getStringExtra("Seat")!![0].digitToInt()
        val arrivaltimeDepart_ = myIntent.getStringExtra("ArrivalTime").toString()

        val pattern = "dd-MM-yyyy'T'HH:mm"
        val sdf = DateTimeFormatter.ofPattern(pattern)
        //val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        //val arrivaltimeDepart = simpleDateFormat.parse(arrivaltimeDepart_)

        var Spinner1Data: ArrayList<Any?> = ArrayList()
        var Spinner2Data: ArrayList<Any?> = ArrayList()
        ReturnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }

        RecyclerViewBusTicket = findViewById<RecyclerView>(R.id.RecyclerViewBusTicket)
        val reentrantLock = ReentrantLock()
        reentrantLock.lock()
        dataList = ArrayList<BusTicket>()
        val databaseReference = Firebase.firestore
        progresssDialog.setMessage("Đang tải dữ liệu...");
        progresssDialog.show();
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
        val departTime = myIntent.getStringExtra("ReturnTime").toString()
        val date = inputFormat.parse(departTime)
        val Date= outputFormat.format(date!!)

        databaseReference.collection("Bus").whereEqualTo("From", destinationpoint).whereEqualTo("To", startingpoint)
            .whereEqualTo("Date", Date)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents)
                {
                    val dataModel= document.toObject(BusTicket::class.java)
                    val departTime = myIntent.getStringExtra("ReturnTime").toString()
                    val departuretimeReturn_ = departTime + "T" + dataModel.DepartureTime
                    Log.d("departuretimeReturn_",departTime+" "+dataModel.DepartureTime)
                    var temp_check = true
                    if (arrivaltimeDepart_ != "")
                    {
                        val departuretimeReturn = LocalDateTime.parse(departuretimeReturn_, sdf)
                        val arrivaltimeDepart = LocalDateTime.parse(arrivaltimeDepart_, sdf)
                        temp_check = arrivaltimeDepart.isBefore(departuretimeReturn)
                        if(arrivaltimeDepart.isBefore(departuretimeReturn))
                            Log.d("Check1", arrivaltimeDepart.isBefore(departuretimeReturn).toString())
                    }
                    //val departuretimeReturn = simpleDateFormat.parse(departuretimeReturn_)
                    if (dataModel.NumSeat.toDouble() >= max_require.toDouble() && temp_check)
                    {
                        Spinner1Data.add(dataModel.PickPoint)
                        Spinner2Data.add(dataModel.DesPoint)
                        dataList.add(dataModel)
                    }
                }
                if (dataList.size == 0)
                {
                    progresssDialog.dismiss();
                    Toast.makeText(this@SelectBusTickets_2Activity, "Không tìm thấy chuyến đi phù hợp", Toast.LENGTH_LONG).show()
                }
                else
                {
                    progresssDialog.dismiss();
                    Spinner1Data.add("Tất cả")
                    Spinner2Data.add("Tất cả")
                    var dataOfRecyclerView: ArrayList<BusTicket> = ArrayList()

                    val Spinner1Data = Spinner1Data.distinct()
                    val StartingPointAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,Spinner1Data)
                    StartingPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner1.onItemSelectedListener = this
                    spinner1.setAdapter(StartingPointAdapter)

                    val Spinner2Data = Spinner2Data.distinct()
                    val DesPointAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,Spinner2Data)
                    DesPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner2.onItemSelectedListener = this
                    spinner2.setAdapter(DesPointAdapter)

                    spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedItem = Spinner1Data[position]
                            var sb_dataList:ArrayList<BusTicket> = ArrayList()
                            (view as TextView).setTextColor(Color.WHITE)
                            if (spinner2.selectedItem.toString() == "Tất cả")
                                sb_dataList = ArrayList<BusTicket>().apply { addAll(dataList) }
                            else
                                for (i in dataList){
                                    if (i.DesPoint == spinner2.selectedItem.toString()) {
                                        sb_dataList.add(i)
                                    }
                                }
                            var index:Int = 0
                            while(index < sb_dataList.size){
                                if (selectedItem.toString() == "Tất cả")
                                    break
                                if (sb_dataList[index].PickPoint != selectedItem.toString()) {
                                    sb_dataList.removeAt(index)
                                    index -= 1
                                }
                                index += 1
                            }
                            val busTicketAdapter = BusTicketAdapter(sb_dataList)
                            dataOfRecyclerView = sb_dataList
                            RecyclerViewBusTicket.adapter = busTicketAdapter
                            RecyclerViewBusTicket.layoutManager = LinearLayoutManager(this@SelectBusTickets_2Activity,LinearLayoutManager.VERTICAL,false)
                            busTicketAdapter.onItemClick = {selectedBusTicket ->
                                val selectedID:String = selectedBusTicket.Id
                                val intent = Intent(this@SelectBusTickets_2Activity, PayActivity::class.java)
                                intent.putExtra("Seat", myIntent.getStringExtra("Seat").toString());
                                intent.putExtra("SecondSelectedID", selectedID);
                                intent.putExtra("FirstSelectedID", myIntent.getStringExtra("FirstSelectedID").toString());
                                intent.putExtra("Tag", "Bus");
                                val LAUNCH_SECOND_ACTIVITY: Int = 1
                                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedItem = Spinner2Data[position]
                            var sb_dataList:ArrayList<BusTicket> = ArrayList()
                            (view as TextView).setTextColor(Color.WHITE)
                            if (spinner1.selectedItem.toString() == "Tất cả")
                                sb_dataList = ArrayList<BusTicket>().apply { addAll(dataList) }
                            else
                                for (i in dataList){
                                    if (i.PickPoint == spinner1.selectedItem.toString()) {
                                        sb_dataList.add(i)
                                    }
                                }
                            var index:Int = 0
                            while(index < sb_dataList.size){
                                if (selectedItem.toString() == "Tất cả")
                                    break
                                if (sb_dataList[index].DesPoint != selectedItem.toString()) {
                                    sb_dataList.removeAt(index)
                                    index -= 1
                                }
                                index += 1
                            }
                            val busTicketAdapter = BusTicketAdapter(sb_dataList)
                            dataOfRecyclerView = sb_dataList
                            RecyclerViewBusTicket.adapter = busTicketAdapter
                            RecyclerViewBusTicket.layoutManager = LinearLayoutManager(this@SelectBusTickets_2Activity,LinearLayoutManager.VERTICAL,false)
                            busTicketAdapter.onItemClick = {selectedBusTicket ->
                                val selectedID:String = selectedBusTicket.Id
                                val intent = Intent(this@SelectBusTickets_2Activity, PayActivity::class.java)
                                intent.putExtra("Seat", myIntent.getStringExtra("Seat").toString());
                                intent.putExtra("SecondSelectedID", selectedID);
                                intent.putExtra("FirstSelectedID", myIntent.getStringExtra("FirstSelectedID").toString());
                                intent.putExtra("Tag", "Bus");
                                val LAUNCH_SECOND_ACTIVITY: Int = 1
                                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }

                    var sorting_btn: FloatingActionButton = findViewById<FloatingActionButton>(R.id.sorting_button)
                    sorting_btn.setOnClickListener {
                        val popupMenu = PopupMenu(this, it)
                        popupMenu.inflate(R.menu.menu_sorting)
                        var newDataOfRecyclerView:List<BusTicket>
                        newDataOfRecyclerView = dataOfRecyclerView
                        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                            when (item.itemId) {
                                R.id.descending_sort -> {
                                    var newDataOfRecyclerView_ = newDataOfRecyclerView.sortedByDescending { it.Price }.toCollection(ArrayList())
                                    val busTicketAdapter = BusTicketAdapter(newDataOfRecyclerView_)
                                    RecyclerViewBusTicket.adapter = busTicketAdapter
                                    RecyclerViewBusTicket.layoutManager = LinearLayoutManager(this@SelectBusTickets_2Activity,LinearLayoutManager.VERTICAL,false)
                                    busTicketAdapter.onItemClick = {selectedBusTicket ->
                                        val selectedID:String = selectedBusTicket.Id
                                        val intent = Intent(this@SelectBusTickets_2Activity, PayActivity::class.java)
                                        intent.putExtra("Seat", myIntent.getStringExtra("Seat").toString());
                                        intent.putExtra("SecondSelectedID", selectedID);
                                        intent.putExtra("FirstSelectedID", myIntent.getStringExtra("FirstSelectedID").toString());
                                        intent.putExtra("Tag", "Bus");
                                        val LAUNCH_SECOND_ACTIVITY: Int = 1
                                        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
                                    }
                                    true
                                }
                                R.id.ascending_sort -> {
                                    var newDataOfRecyclerView_ = newDataOfRecyclerView.sortedBy { it.Price }.toCollection(ArrayList())
                                    val busTicketAdapter = BusTicketAdapter(newDataOfRecyclerView_)
                                    RecyclerViewBusTicket.adapter = busTicketAdapter
                                    RecyclerViewBusTicket.layoutManager = LinearLayoutManager(this@SelectBusTickets_2Activity,LinearLayoutManager.VERTICAL,false)
                                    busTicketAdapter.onItemClick = {selectedBusTicket ->
                                        val selectedID:String = selectedBusTicket.Id
                                        val intent = Intent(this@SelectBusTickets_2Activity, PayActivity::class.java)
                                        intent.putExtra("Seat", myIntent.getStringExtra("Seat").toString());
                                        intent.putExtra("SecondSelectedID", selectedID);
                                        intent.putExtra("FirstSelectedID", myIntent.getStringExtra("FirstSelectedID").toString());
                                        intent.putExtra("Tag", "Bus");
                                        val LAUNCH_SECOND_ACTIVITY: Int = 1
                                        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
                                    }
                                    true
                                }
                                else -> false
                            }
                        }
                        popupMenu.show()
                    }
                }
            }
            .addOnFailureListener{exception ->
                Log.w("Error getting documents: ", exception)
            }
        reentrantLock.unlock()
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("Selected","Select Seat")
        (view as TextView).setTextColor(Color.WHITE)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("Selected","Not select seat")
    }
}