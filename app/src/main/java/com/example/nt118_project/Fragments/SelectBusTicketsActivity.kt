package com.example.nt118_project.Fragments

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nt118_project.R
import org.w3c.dom.Text

class SelectBusTicketsActivity : AppCompatActivity() {
    private lateinit var tv_search: TextView
    private lateinit var tvSeat: TextView
    private lateinit var DepartureDaytV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_bus_tickets)

        val myIntent = intent // this is just for example purpose
        val startingpoint:String = myIntent.getStringExtra("Starting Point").toString()
        val destinationpoint:String = myIntent.getStringExtra("Destination Point").toString()
        val ReturnBtn = findViewById<ImageView>(R.id.iVBack)
        tv_search = findViewById<TextView>(R.id.tv_search)
        DepartureDaytV = findViewById<TextView>(R.id.DepartureDaySpinner)
        tvSeat = findViewById<TextView>(R.id.tVSeat)
        tv_search.text = startingpoint + " -> " + destinationpoint
        DepartureDaytV.text = myIntent.getStringExtra("DepartTime").toString()
        tvSeat.text = myIntent.getStringExtra("Seat").toString()
        ReturnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
    }
}