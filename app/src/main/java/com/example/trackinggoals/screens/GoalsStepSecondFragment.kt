package com.example.trackinggoals.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentGoalsStepFirstBinding
import com.example.trackinggoals.databinding.FragmentGoalsStepSecondBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator
import java.util.*

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


        viewModel.unit.observe(viewLifecycleOwner){
            binding.autoCompleteTextGoalsStepSecondUnit.setText(it)
        }
        viewModel.quantity.observe(viewLifecycleOwner){
            binding.editTextGoalsStepSecondWant.setText(it.toString())
        }
        viewModel.criterion.observe(viewLifecycleOwner){
            binding.editTextGoalsStepSecondCriterion.setText(it)
        }

        val items = resources.getStringArray(R.array.unit)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.autoCompleteTextGoalsStepSecondUnit.setAdapter(adapter)

        binding.autoCompleteTextGoalsStepSecondUnit.setOnItemClickListener { parent, _, position, _ ->
            var item = parent.getItemAtPosition(position).toString()
        }

        binding.buttonGoalsStepSecondComplete.setOnClickListener {
            val goalsId = requireArguments().getInt(ARG_GOALS_ID)
            viewModel.updateQuantityGoals(binding.editTextGoalsStepSecondWant.text.toString().toInt(), goalsId)
            viewModel.updateUnitGoals(binding.autoCompleteTextGoalsStepSecondUnit.text.toString(), goalsId)
            viewModel.updateCriterionGoals(binding.editTextGoalsStepSecondCriterion.text.toString()
                .uppercase(Locale.getDefault()),goalsId)
            navigator().goBaseMenu()
        }
        binding.imageViewBackStepSecond.setOnClickListener {
            val goalsId = requireArguments().getInt(ARG_GOALS_ID)
            navigator().showGoalsStepFirst(goalsId)
        }

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