package com.example.trackinggoals.screens.tab.goals.list.achieved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentGoalsListAchievedBinding
import com.example.trackinggoals.findTopNavController
import com.example.trackinggoals.model.goals.entities.Goals
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.screens.dialogs.CustomInputDialogFragment
import com.example.trackinggoals.screens.dialogs.CustomInputDialogListener
import com.example.trackinggoals.screens.tab.TabsFragmentDirections
import com.example.trackinggoals.screens.tab.goals.list.GoalsActionListener
import com.example.trackinggoals.screens.tab.goals.list.GoalsAdapter
import com.example.trackinggoals.viewModelCreator

class GoalsListAchievedFragment : Fragment() {
    private lateinit var binding: FragmentGoalsListAchievedBinding
    private lateinit var adapter: GoalsAdapter

    private val viewModel by viewModelCreator { GoalsListAchievedViewModel(Repositories.goalsRepository) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalsListAchievedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GoalsAdapter(object : GoalsActionListener {
            override fun onEditGoals(goals: Goals) {
//                navigator().showGoalsConstructor(goals.id)
            }

            override fun onEditStatusGoals(goals: Goals) {
                viewModel.editStatusGoals(goals.isActive, goals.id)
            }

            override fun onRemoveGoals(goals: Goals) {
                viewModel.removeGoals(goals.id)

            }

            override fun openDialogEditProgress(goals: Goals) {
                showCustomInputDialogFragment(KEY_FIRST_REQUEST_KEY)
                setupCustomInputDialogFragmentListeners(goals)
            }
        })
        binding.floatingActionButtonGoalsListAchieved.setOnClickListener {
            val direction = TabsFragmentDirections.actionTabsFragmentToGoalsConstructorFragment(1)
            findTopNavController().navigate(direction)
        }
        binding.buttonToolbarGoalsListAchieved.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
            adapter.goals = it
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewGoalsListAchieved.layoutManager = layoutManager
        binding.recyclerViewGoalsListAchieved.adapter = adapter

    }

    private fun showCustomInputDialogFragment(requestKey: String) {
        CustomInputDialogFragment.show(parentFragmentManager, requestKey)
    }

    private fun setupCustomInputDialogFragmentListeners(goals: Goals) {
        val listener: CustomInputDialogListener = { requestKey, progress ->
            when (requestKey) {
                KEY_FIRST_REQUEST_KEY -> viewModel.updateProgress(
                    progress,
                    goals.id,
                    resources.getString(R.string.text_new_result)
                )
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
    }
}