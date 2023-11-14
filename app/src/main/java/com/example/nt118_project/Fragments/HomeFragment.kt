package com.example.nt118_project.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.nt118_project.R
import com.example.nt118_project.flight.SearchFlightActivity
import com.example.nt118_project.hotel.SearchHotelActivity
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private  lateinit var  viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager2 = view.findViewById(R.id.viewpaper2)
        adapter = FragmentPageAdapter(childFragmentManager,lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("Khuyến mãi"))
        tabLayout.addTab(tabLayout.newTab().setText("Dành cho bạn"))


        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!=null){
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        val BusTicketBtn = view.findViewById<ImageButton>(R.id.img_bus)
        BusTicketBtn.setOnClickListener {
            val intent = Intent(activity, BookBusTicketsActivity::class.java)
            val LAUNCH_SECOND_ACTIVITY:Int = 1
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
        }
        val searchFlyBtn = view.findViewById<ImageButton>(R.id.img_airplane)
        searchFlyBtn.setOnClickListener {
            val intent = Intent(activity, SearchFlightActivity::class.java)
            val LAUNCH_SECOND_ACTIVITY:Int = 1
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
        }
        val searchHotelBtn = view.findViewById<ImageButton>(R.id.img_hotel)
        searchHotelBtn.setOnClickListener {
            val intent = Intent(activity, SearchHotelActivity::class.java)
            val LAUNCH_SECOND_ACTIVITY:Int = 1
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
        }
    }

}