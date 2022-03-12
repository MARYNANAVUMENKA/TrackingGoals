package com.example.trackinggoals.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentGoalsStepFirstBinding
import com.example.trackinggoals.databinding.FragmentGoalsStepSecondBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.viewModelCreator

class GoalsStepSecondFragment: Fragment() {
    private lateinit var binding: FragmentGoalsStepSecondBinding
    private val viewModel by viewModelCreator {  GoalsStepSecondViewModel(Repositories.goalsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val goalsId = requireArguments().getInt(ARG_GOALS_ID)
        viewModel.loadGoals(goalsId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalsStepSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.criterion.observe(viewLifecycleOwner){

        }
        viewModel.unit.observe(viewLifecycleOwner){

        }
        viewModel.quantity.observe(viewLifecycleOwner){

        }
        binding.editTextGoalsStepSecondWant.setOnClickListener {

        }
        binding.editTextGoalsStepSecondCriterion.setOnClickListener {

        }
        binding


    }
    companion object {
        private const val ARG_GOALS_ID = "ARG_GOALS_ID"

        fun newInstance(goalsId: Int): GoalsStepSecondFragment {
            val fragment = GoalsStepSecondFragment()
            fragment.arguments = bundleOf(ARG_GOALS_ID to goalsId)
            return fragment
        }
    }
}