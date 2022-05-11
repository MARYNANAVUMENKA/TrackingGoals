package com.example.trackinggoals.screens.tab.goals.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentStartGoalsBinding
import com.example.trackinggoals.findTopNavController
import com.example.trackinggoals.screens.tab.TabsFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GoalsStartFragment : Fragment() {

    private  var _binding: FragmentStartGoalsBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModel<GoalsStartViewModel> { parametersOf() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                findNavController().navigate(R.id.action_goalsStartFragment_to_goalsListActiveFragment)
            }
        }

        binding.buttonToolbarConstructorGoals.setOnClickListener {
            findNavController().navigate(R.id.action_goalsStartFragment_to_goalsListAchievedFragment)
        }

        binding.buttonConstructorGoalsStart.setOnClickListener {
            val direction = TabsFragmentDirections.actionTabsFragmentToGoalsConstructorFragment(1)
            findTopNavController().navigate(direction)
        }
    }
}