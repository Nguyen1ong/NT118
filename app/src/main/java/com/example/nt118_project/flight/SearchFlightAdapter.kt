package com.example.nt118_project.flight

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nt118_project.Fragments.Flight1Fragment
import com.example.nt118_project.Fragments.Flight2Fragment

class SearchFlightAdapter
    (fragmentManager:FragmentManager,lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position==0)
            Flight1Fragment()
        else
            Flight2Fragment()
    }
     fun getHeight(position: Int): Int? {
        return if (position==0)
            Flight1Fragment().view?.height
        else
            Flight2Fragment().view?.height
    }

}