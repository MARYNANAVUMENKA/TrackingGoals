package com.example.trackinggoals.screens.goals.list.active

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.FragmentGoalsListBinding
import com.example.trackinggoals.model.goals.entities.Goals
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.screens.CustomInputDialogFragment
import com.example.trackinggoals.screens.CustomInputDialogListener
import com.example.trackinggoals.screens.goals.list.GoalsActionListener
import com.example.trackinggoals.screens.goals.list.GoalsAdapter
import com.example.trackinggoals.viewModelCreator

class GoalsListActiveFragment : Fragment() {
    private lateinit var binding: FragmentGoalsListBinding
    private lateinit var adapter: GoalsAdapter
    private val viewModel by viewModelCreator { GoalsListActiveViewModel(Repositories.goalsRepository) }


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
                navigator().showGoalsConstructor(goals.id)
            }

            override fun onEditStatusGoals(goals: Goals) {
                viewModel.editStatusGoals(goals.isActive, goals.id)
                navigator().goBaseMenu()
            }

            override fun onRemoveGoals(goals: Goals) {
                viewModel.removeGoals(goals.id)

            }

            override fun openDialogEditProgress(goals: Goals) {
                showCustomInputDialogFragment(KEY_FIRST_REQUEST_KEY)
                setupCustomInputDialogFragmentListeners(goals)
            }
        })

        binding.buttonToolbarGoalsList.setOnClickListener {

        }
        binding.floatingActionButtonGoalsList.setOnClickListener {
            viewModel.createEmptyGoals()
            viewModel.idGoalsLiveData.observe(viewLifecycleOwner) {
                navigator().showGoalsConstructor(it)
            }
        }
        viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                navigator().goBaseMenu()
            }
            adapter.goals = it
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewGoalsList.layoutManager = layoutManager
        binding.recyclerViewGoalsList.adapter = adapter

    }

    private fun showCustomInputDialogFragment(requestKey: String) {
        CustomInputDialogFragment.show(parentFragmentManager, requestKey)
    }

    private fun setupCustomInputDialogFragmentListeners(goals: Goals) {
        val listener: CustomInputDialogListener = { requestKey, progress ->
            when (requestKey) {
                KEY_FIRST_REQUEST_KEY -> viewModel.updateProgress(progress, goals.id)
            }
        }
        CustomInputDialogFragment.setupListener(
            parentFragmentManager,
            this,
            KEY_FIRST_REQUEST_KEY,
            listener
        )
    }

    companion object {
        @JvmStatic
        private val KEY_FIRST_REQUEST_KEY = "KEY_FIRST_REQUEST_KEY"

        fun newInstance(): GoalsListActiveFragment {
            return GoalsListActiveFragment()
        }
    }
}