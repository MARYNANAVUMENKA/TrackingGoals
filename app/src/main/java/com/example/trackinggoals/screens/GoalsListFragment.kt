package com.example.trackinggoals.screens

import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentGoalsListBinding

class GoalsListFragment: Fragment() {
    private lateinit var binding:FragmentGoalsListBinding





    companion object {
        fun newInstance(): GoalsListFragment {
            return GoalsListFragment()
        }
    }
}