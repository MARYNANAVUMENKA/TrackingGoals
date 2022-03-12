package com.example.trackinggoals.screens

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentGoalsStepFirstBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.screens.GoalsConstructorFragment
import com.example.trackinggoals.viewModelCreator

class GoalsStepFirstFragment:Fragment() {
    private lateinit var binding:FragmentGoalsStepFirstBinding
    private val viewModel by viewModelCreator {  GoalsStepFirstViewModel(Repositories.goalsRepository) }

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
        binding =FragmentGoalsStepFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.textGoals.observe(viewLifecycleOwner){
            binding.editTextInputGoalsStepFirst.setText(it)
        }
        viewModel.photo.observe(viewLifecycleOwner){
//            if (it==""){
//
//            }

        }

        viewModel.dataExecution.observe(viewLifecycleOwner){
            binding.buttonGoalsStepSecondChooseDat.setText(it)

        }

        binding.buttonGoalsStepFirstChoosePic.setOnClickListener {
            val photoBottomSheetDialogFragment = PhotoBottomSheetDialogFragment()
            photoBottomSheetDialogFragment.show(childFragmentManager, PhotoBottomSheetDialogFragment.TAG)
        }



        binding.buttonGoalsStepFirstNext.setOnClickListener {
            val goalsId = requireArguments().getInt(ARG_GOALS_ID)
            when (binding.editTextInputGoalsStepFirst.text.toString()) {
                "" -> showAlertDialogStepFirstNext()
                else -> {
                    updateGoals()
                    navigator().showGoalsStepSecond(goalsId)
                }
            }
        }
         binding.imageButtomBackStepFirst.setOnClickListener {
             when (binding.editTextInputGoalsStepFirst.text.toString()) {
                 "" -> showAlertDialogStepFirstBack()
                 else -> {
                     //возврат на fragment_list_goals
                 }
             }
         }


    }
    private fun updateGoals() {
        val textGoals = binding.editTextInputGoalsStepFirst.text.toString()
        val goalsId = requireArguments().getInt(ARG_GOALS_ID)
        if (goalsId == 1) {
                viewModel.saveGoals(goalsId,textGoals)
        } else {
                viewModel.editGoals(goalsId,textGoals)
        }
    }
    private fun showAlertDialogStepFirstBack() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> navigator().goBaseMenu()
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_exit_title)
            .setMessage(R.string.alert_dialog_exit_text)
            .setPositiveButton(R.string.alert_dialog_action_exit, listener)
            .setNegativeButton(R.string.alert_dialog_action_return, listener)
            .create()
        dialog.show()
    }

    private fun showAlertDialogStepFirstNext() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> navigator().goBaseMenu()
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_next_title)
            .setMessage(R.string.alert_dialog_next_text)
            .setPositiveButton(R.string.alert_dialog_action_next, listener)
            .setNegativeButton(R.string.alert_dialog_action_next_return, listener)
            .create()
        dialog.show()
    }


    companion object {
        private const val ARG_GOALS_ID = "ARG_GOALS_ID"

        fun newInstance(goalsId: Int): GoalsStepFirstFragment {
            val fragment = GoalsStepFirstFragment()
            fragment.arguments = bundleOf(ARG_GOALS_ID to goalsId)
            return fragment
        }
    }
}