package com.example.trackinggoals.presentation.tab.goals.list.achieved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentGoalsListAchievedBinding
import com.example.trackinggoals.domain.model.Goals
import com.example.trackinggoals.presentation.dialogs.CustomInputDialogFragment
import com.example.trackinggoals.presentation.dialogs.CustomInputDialogListener
import com.example.trackinggoals.presentation.tab.TabsFragmentDirections
import com.example.trackinggoals.presentation.tab.goals.list.GoalsActionListener
import com.example.trackinggoals.presentation.tab.goals.list.GoalsAdapter
import com.example.trackinggoals.util.findTopNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GoalsListAchievedFragment : Fragment() {
    private  var _binding: FragmentGoalsListAchievedBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: GoalsAdapter

    private val viewModel by viewModel<GoalsListAchievedViewModel> { parametersOf() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoalsListAchievedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GoalsAdapter(object : GoalsActionListener {
            override fun onEditGoals(goals: Goals) {
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