package com.example.nt118_project.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.View
import kotlin.random.Random
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nt118_project.MainActivity
import com.example.nt118_project.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class PayActivity : AppCompatActivity() {
    private lateinit var FillUserRadio: RadioButton
    private lateinit var ReviewUserRadio: RadioButton
    private lateinit var PayUserRadio: RadioButton
    private lateinit var LayoutContainer: FrameLayout
    private lateinit var RadioGroup: RadioGroup
    private lateinit var ReturnBtn:ImageView
    private lateinit var HomeBtn:ImageView
    private lateinit var NextBtn: Button
    private var FirstID:String = ""
    private var SecondID: String = ""
    private var NumberOfSeat: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        var RoomID:String = ""
        var DayStart:String = ""
        var DayEnd:String = ""
        var NumRoom:String = ""

        var isDriver:String = ""

        var CarID:String = ""
        var TimeStart:String = ""
        var TimeEnd:String = ""
        var PlacePick:String = ""
        var Duration:String = ""

        val myIntent = intent // this is just for example purpose
        val tag_ = myIntent.getStringExtra("Tag").toString()
        if(tag_ == "Bus" || tag_ == "Flight")
        {
            FirstID = myIntent.getStringExtra("FirstSelectedID").toString()
            SecondID = myIntent.getStringExtra("SecondSelectedID").toString()
            NumberOfSeat = myIntent.getStringExtra("Seat").toString()
        }
        else if(tag_ == "Hotel")
        {
            RoomID = myIntent.getStringExtra("SelectedID").toString()
            DayStart = myIntent.getStringExtra("DayStart").toString()
            DayEnd = myIntent.getStringExtra("DayEnd").toString()
            NumRoom = myIntent.getStringExtra("NumRoom").toString()
        }
        else
        {
            isDriver = myIntent.getStringExtra("driver").toString()
            if(isDriver == "true")
            {
                CarID = myIntent.getStringExtra("SelectedID").toString()
                TimeStart = myIntent.getStringExtra("TimeStart").toString()
                TimeEnd = myIntent.getStringExtra("TimeEnd").toString()
                PlacePick = myIntent.getStringExtra("Place").toString()
                Duration = myIntent.getStringExtra("Duration").toString()
                DayStart = myIntent.getStringExtra("DateStart").toString()
                DayEnd = myIntent.getStringExtra("DateEnd").toString()
            }
            else
            {
                CarID = myIntent.getStringExtra("CarSelectedID").toString()
                TimeStart = myIntent.getStringExtra("TimeStart").toString()
                PlacePick = myIntent.getStringExtra("Place").toString()
                DayStart = myIntent.getStringExtra("DateStart").toString()
                DayEnd = myIntent.getStringExtra("DateEnd").toString()
                TimeEnd = myIntent.getStringExtra("TimeEnd").toString()
                Duration = ""
            }
        }


        FillUserRadio = findViewById<RadioButton>(R.id.radio_button1)
        ReviewUserRadio = findViewById<RadioButton>(R.id.radio_button2)
        PayUserRadio = findViewById<RadioButton>(R.id.radio_button3)
        LayoutContainer = findViewById<FrameLayout>(R.id.frame_layout)
        RadioGroup = findViewById<RadioGroup>(R.id.radio_group)
        ReturnBtn = findViewById<ImageView>(R.id.iVBack)
        NextBtn = findViewById<Button>(R.id.Savebutton)
        HomeBtn = findViewById(R.id.iVHome)

        FillUserRadio.isEnabled = true

        RadioGroup.setOnCheckedChangeListener {group, checkedId ->
            when(checkedId){
                R.id.radio_button2 -> {
                    ReviewUserRadio.isEnabled = true
                    val fragobj = ReviewUserInfoFragmentFragment()
                    val bundle = Bundle()
                    if(tag_ == "Bus" || tag_ == "Flight")
                    {
                        bundle.putString("FirstID", FirstID)
                        bundle.putString("SecondID", SecondID)
                        bundle.putString("Tag", tag_)
                    }
                    else if(tag_ == "Hotel")
                    {
                        bundle.putString("SelectedID", RoomID)
                        bundle.putString("DayStart", DayStart)
                        bundle.putString("NumRoom", NumRoom)
                        bundle.putString("DayEnd", DayEnd)
                        bundle.putString("Tag", tag_)
                    }
                    else
                    {
                        bundle.putString("SelectedID", CarID)
                        bundle.putString("DayStart", DayStart)
                        bundle.putString("DayEnd", DayEnd)
                        bundle.putString("TimeStart", TimeStart)
                        bundle.putString("PlacePick",PlacePick)
                        bundle.putString("TimeEnd",TimeEnd)
                        bundle.putString("Duration",Duration)
                        bundle.putString("isDriver",isDriver)
                        bundle.putString("Tag", tag_)
                    }
                    fragobj.setArguments(bundle)
                    replaceFragment(fragobj)
                    NextBtn.text = "Tiếp tục"
                    NextBtn.setVisibility(View.VISIBLE)
                }
                R.id.radio_button3 -> {
                    PayUserRadio.isEnabled = true
                    val fragobj = PaymentInfoFragment()
                    val bundle = Bundle()
                    if(tag_ == "Bus" || tag_ == "Flight")
                    {
                        bundle.putString("FirstID", FirstID)
                        bundle.putString("SecondID", SecondID)
                        bundle.putString("Seat", NumberOfSeat)
                        bundle.putString("Tag", tag_)
                    }
                    else if(tag_ == "Hotel")
                    {
                        bundle.putString("SelectedID", RoomID)
                        bundle.putString("DayStart", DayStart)
                        bundle.putString("NumRoom", NumRoom)
                        bundle.putString("DayEnd", DayEnd)
                        bundle.putString("Tag", tag_)
                    }
                    else
                    {
                        bundle.putString("SelectedID", CarID)
                        bundle.putString("DayStart", DayStart)
                        bundle.putString("DayEnd", DayEnd)
                        bundle.putString("TimeStart", TimeStart)
                        bundle.putString("PlacePick",PlacePick)
                        bundle.putString("TimeEnd",TimeEnd)
                        bundle.putString("Duration",Duration)
                        bundle.putString("isDriver",isDriver)
                        bundle.putString("Tag", tag_)
                    }
                    fragobj.setArguments(bundle)
                    replaceFragment(fragobj)
                    NextBtn.text = "Hoàn tất"
                    NextBtn.setVisibility(View.GONE)
                }
                R.id.radio_button1 -> {
                    val fragobj = FillUserInfoFragment()
                    val bundle = Bundle()
                    if(tag_ == "Bus" || tag_ == "Flight")
                    {
                        bundle.putString("FirstID", FirstID)
                        bundle.putString("SecondID", SecondID)
                        bundle.putString("Tag", tag_)
                    }
                    else if(tag_ == "Hotel")
                    {
                        bundle.putString("SelectedID", RoomID)
                        bundle.putString("DayStart", DayStart)
                        bundle.putString("NumRoom", NumRoom)
                        bundle.putString("DayEnd", DayEnd)
                        bundle.putString("Tag", tag_)
                    }
                    else
                    {
                        bundle.putString("SelectedID", CarID)
                        bundle.putString("DayStart", DayStart)
                        bundle.putString("DayEnd", DayEnd)
                        bundle.putString("TimeStart", TimeStart)
                        bundle.putString("PlacePick",PlacePick)
                        bundle.putString("TimeEnd",TimeEnd)
                        bundle.putString("Duration",Duration)
                        bundle.putString("isDriver",isDriver)
                        bundle.putString("Tag", tag_)
                    }
                    fragobj.setArguments(bundle)
                    replaceFragment(fragobj)
                    NextBtn.text = "Tiếp tục"
                    NextBtn.setVisibility(View.VISIBLE)
                }
            }
        }

        FillUserRadio.performClick()

        ReturnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
        HomeBtn.setOnClickListener {
            val intent = Intent(this@PayActivity, MainActivity::class.java)
            val LAUNCH_SECOND_ACTIVITY:Int = 1
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
        }
        NextBtn.setOnClickListener {
            val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.frame_layout)

            if (currentFragment is FillUserInfoFragment) {
                ReviewUserRadio.performClick()
            } else if (currentFragment is ReviewUserInfoFragmentFragment) {
                PayUserRadio.performClick()
            }
        }

    }
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}