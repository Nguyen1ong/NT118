package com.example.nt118_project.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nt118_project.R

class PayActivity : AppCompatActivity() {
    private lateinit var FillUserRadio: RadioButton
    private lateinit var ReviewUserRadio: RadioButton
    private lateinit var PayUserRadio: RadioButton
    private lateinit var LayoutContainer: FrameLayout
    private lateinit var RadioGroup: RadioGroup
    private lateinit var ReturnBtn:ImageView
    private lateinit var NextBtn: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        FillUserRadio = findViewById<RadioButton>(R.id.radio_button1)
        ReviewUserRadio = findViewById<RadioButton>(R.id.radio_button2)
        PayUserRadio = findViewById<RadioButton>(R.id.radio_button3)
        LayoutContainer = findViewById<FrameLayout>(R.id.frame_layout)
        RadioGroup = findViewById<RadioGroup>(R.id.radio_group)
        ReturnBtn = findViewById<ImageView>(R.id.iVBack)
        NextBtn = findViewById<Button>(R.id.Savebutton)


        FillUserRadio.isEnabled = true
        FillUserRadio.performClick()
        replaceFragment(FillUserInfoFragment())
        FillUserRadio.setOnClickListener {
            replaceFragment(FillUserInfoFragment())
        }
        ReviewUserRadio.setOnClickListener {
            ReviewUserRadio.isEnabled = true
            replaceFragment(ReviewUserInfoFragmentFragment())
        }
        ReturnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
        NextBtn.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val myFragment: FillUserInfoFragment? = supportFragmentManager.findFragmentByTag("FillUserInfoFragment") as FillUserInfoFragment?
            val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.frame_layout)

            if (currentFragment is FillUserInfoFragment) {
                ReviewUserRadio.performClick()
                //replaceFragment(ReviewUserInfoFragmentFragment())
            } else if (currentFragment is ReviewUserInfoFragmentFragment) { }
        }

    }
    public fun onClick(v: View)
    {
        when(v.id)
        {
           R.id.radio_button1 -> {replaceFragment(FillUserInfoFragment())}
           R.id.radio_button2 -> {replaceFragment(ReviewUserInfoFragmentFragment())}
           R.id.radio_button3 -> {}
        }
    }
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}