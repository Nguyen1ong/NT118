package com.example.nt118_project.Fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nt118_project.Adapter.BusTicketAdapter
import com.example.nt118_project.Model.BusTicket
import com.example.nt118_project.R

class SelectBusTickets_2Activity : AppCompatActivity() {
    private lateinit var tv_search: TextView
    private lateinit var RecyclerViewBusTicket: RecyclerView
    private lateinit var tvSeat: TextView
    private lateinit var DepartureDaytV: TextView
    private lateinit var dataList:ArrayList<BusTicket>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_bus_tickets2)

        val myIntent = intent // this is just for example purpose
        val startingpoint:String = myIntent.getStringExtra("Starting Point").toString()
        val destinationpoint:String = myIntent.getStringExtra("Destination Point").toString()
        val ReturnBtn = findViewById<ImageView>(R.id.iVBack)
        tv_search = findViewById<TextView>(R.id.tv_search)
        DepartureDaytV = findViewById<TextView>(R.id.DepartureDaySpinner)
        tvSeat = findViewById<TextView>(R.id.tVSeat)
        tv_search.text = destinationpoint + " -> " + startingpoint
        DepartureDaytV.text = myIntent.getStringExtra("ReturnTime").toString()
        tvSeat.text = myIntent.getStringExtra("Seat").toString()
        ReturnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }

        RecyclerViewBusTicket = findViewById<RecyclerView>(R.id.RecyclerViewBusTicket)
        dataList = ArrayList<BusTicket>()
        var firstBusTicket: BusTicket = BusTicket("Hoa Mai", "16", "10:00","13:00","3h","Văn Phòng 1","Văn phòng 2","100.000")
        dataList.add(firstBusTicket)
        var busTicketAdapter = BusTicketAdapter(dataList)
        RecyclerViewBusTicket.adapter = busTicketAdapter
        RecyclerViewBusTicket.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        busTicketAdapter.onItemClick = {
            val intent = Intent(this, PayActivity::class.java)
            intent.putExtra("Starting Point", startingpoint);
            intent.putExtra("Destination Point", destinationpoint);
            intent.putExtra("DepartTime", DepartureDaytV.text.toString());
            intent.putExtra("ReturnTime", myIntent.getStringExtra("ReturnTime").toString());
            intent.putExtra("Seat", myIntent.getStringExtra("Seat").toString());
            val LAUNCH_SECOND_ACTIVITY:Int = 1
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
        }
    }
}