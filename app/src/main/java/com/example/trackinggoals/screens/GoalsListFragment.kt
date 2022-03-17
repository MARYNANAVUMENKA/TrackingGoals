package com.example.trackinggoals.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.FragmentGoalsListBinding
import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator

class GoalsListFragment : Fragment() {
    private lateinit var binding: FragmentGoalsListBinding
    private lateinit var adapter: GoalsAdapter
    private val viewModel by viewModelCreator { GoalsListViewModel(Repositories.goalsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GoalsAdapter(object : GoalsActionListener {
            override fun onEditGoals(goals: Goals) {
                navigator().showGoalsStepFirst(goals.id)
            }

            override fun onEditStatusGoals(goals: Goals) {
                viewModel.editStatusGoals(goals.isActive, goals.id)

            }

            override fun onRemoveGoals(goals: Goals) {
                viewModel.removeGoals(goals.id)

            }
        })
        binding.floatingActionButtonGoalsList.setOnClickListener {
            viewModel.createEmptyGoals()
            viewModel.idGoals.observe(viewLifecycleOwner) {
                navigator().showGoalsStepFirst(it)
            }
        }

        viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
            adapter.goals = it
        }


        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewGoalsList.layoutManager = layoutManager
        binding.recyclerViewGoalsList.adapter = adapter

    }

    companion object {
        fun newInstance(): GoalsListFragment {
            return GoalsListFragment()
        }
    }
}