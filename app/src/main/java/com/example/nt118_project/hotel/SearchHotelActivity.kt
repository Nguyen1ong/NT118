package com.example.nt118_project.hotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.nt118_project.R

class SearchHotelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_hotel)
        val listHotelBtn = findViewById<Button>(R.id.btn_search)
        listHotelBtn.setOnClickListener{
            val intent = Intent(this, ListofHotelsActivity::class.java)
            val LAUNCH_SECOND_ACTIVITY:Int = 1
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
        }
        val backBtn = findViewById<ImageButton>(R.id.search_hotel_back)
        backBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
    }
}