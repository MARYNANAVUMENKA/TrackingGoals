package com.example.trackinggoals.screens.goals.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentStartGoalsBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator


class GoalsStartFragment : Fragment() {

    private lateinit var binding: FragmentStartGoalsBinding

    private val viewModel by viewModelCreator { GoalsStartViewModel(Repositories.goalsRepository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonConstructorGoalsStart.setOnClickListener {
            viewModel.createEmptyGoals()
            viewModel.idGoals.observe(viewLifecycleOwner) {
                navigator().showGoalsConstructor(it)
            }
        }
    }

    companion object {

        fun newInstance(): GoalsStartFragment {
            return GoalsStartFragment()
        }
    }
}