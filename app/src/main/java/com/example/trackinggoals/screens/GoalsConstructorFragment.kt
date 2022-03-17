package com.example.trackinggoals.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentConstructorGoalsBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator


class GoalsConstructorFragment: Fragment() {

    private lateinit var binding : FragmentConstructorGoalsBinding

    private val viewModel by viewModelCreator { GoalsConstructorViewModel(Repositories.goalsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConstructorGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonConstructorGoalsStart.setOnClickListener {
            viewModel.createEmptyGoals()
            viewModel.idGoals.observe(viewLifecycleOwner){
                navigator().showGoalsStepFirst(it)
            }
        }
    }

    companion object {

        fun newInstance(): GoalsConstructorFragment {
            return GoalsConstructorFragment()
        }
    }
}