package com.example.nt118_project

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar.Tab
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.nt118_project.Fragments.BillFragment
import com.example.nt118_project.Fragments.FragmentPageAdapter
import com.example.nt118_project.Fragments.HomeFragment
import com.example.nt118_project.Fragments.SearchFragment
import com.example.nt118_project.Fragments.UserFragment
import com.example.nt118_project.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private  lateinit var  viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())


        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.bill -> replaceFragment(BillFragment())
                R.id.user -> {
                    val bundle = Bundle()
                    bundle.putString("Pw", intent.getStringExtra("Pw").toString())
                    val fragobj = UserFragment()
                    fragobj.setArguments(bundle)
                    replaceFragment(fragobj)}
                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }

}