package com.example.trackinggoals.screens


import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentIncomingBinding
import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.Incoming
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator

class IncomingFragment() : Fragment() {
    private lateinit var binding: FragmentIncomingBinding
    private val viewModel by viewModelCreator { IncomingViewModel(Repositories.incomingRepository) }
    private lateinit var currentIncoming: Incoming

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val incomingId = requireArguments().getInt(ARG_INCOMING_ID)
        val noteId = requireArguments().getInt(ARG_NOTE_ID)
        val currentDataIn=requireArguments().getString(ARG_INCOMING_DATA)

        if (currentDataIn != null) {
            viewModel.loadIncoming(incomingId,noteId,currentDataIn)
        }
        viewModel.loadGoals()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextIncoming.requestFocus()
        showKeyboard(binding.editTextIncoming)
        setupGoalsBottomSheetDialogFragmentListeners()

        viewModel.incoming.observe(viewLifecycleOwner){
            currentIncoming = it
        }
        viewModel.currentData.observe(viewLifecycleOwner) {
            binding.textViewData.text = it
        }
        viewModel.textMessage.observe(viewLifecycleOwner) {
            binding.editTextIncoming.setText(it)
        }
        viewModel.textGoals.observe(viewLifecycleOwner){
            binding.textViewIncomingTextGoals.text=it
        }
        viewModel.quantity.observe(viewLifecycleOwner){
            binding.textViewIncomingQuantityGoals.text=it
        }

        binding.imageViewBack.setOnClickListener {
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    navigator().goBaseMenu()
                }
            }
        }

        binding.imageViewDone.setOnClickListener {
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    updateIncoming()
                    navigator().goBaseMenu()
                }
            }
        }

        binding.imageViewDelete.setOnClickListener {
            showAlertDialogDelete()
        }
        binding.cardInner.setOnClickListener {
            viewModel.listGoalsLiveData.observe(viewLifecycleOwner){
                showGoalsBottomSheetDialogFragment(KEY_FIRST_GOALS_REQUEST_KEY, it)

            }
        }
    }
    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun showGoalsBottomSheetDialogFragment(requestKey: String, listGoals: ArrayList<String>) {
        GoalsBottomSheetDialogFragment.show(parentFragmentManager, listGoals, requestKey)
    }

    private fun setupGoalsBottomSheetDialogFragmentListeners() {
        val listener: GoalsBottomSheetDialogFragmentListener = { requestKey, textGoals ->
            when (requestKey) {
                KEY_FIRST_GOALS_REQUEST_KEY -> {
                    viewModel.getSelectedGoals(textGoals)
                    showCustomInputDialogFragment(KEY_INPUT_REQUEST_KEY)
                }
//                KEY_SECOND_REQUEST_KEY -> this.secondVolume = volume
            }
            viewModel.selectedGoals.observe(viewLifecycleOwner){
                binding.textViewIncomingTextGoals.text = it.textGoals
                setupCustomInputDialogFragmentListeners(it)

            }
        }
        GoalsBottomSheetDialogFragment.setupListener(parentFragmentManager, this, KEY_FIRST_GOALS_REQUEST_KEY, listener)

    }

    private fun showCustomInputDialogFragment(requestKey: String) {
        CustomInputDialogFragment.show(parentFragmentManager, requestKey)
    }

    private fun setupCustomInputDialogFragmentListeners(goals: Goals) {
        val listener: CustomInputDialogListener = { requestKey, progress ->
            when (requestKey) {
                KEY_INPUT_REQUEST_KEY -> {
                    viewModel.updateProgress(progress,goals.id)
                    binding.textViewIncomingQuantityGoals.text = progress
                }
            }
        }
        CustomInputDialogFragment.setupListener(parentFragmentManager, this, KEY_INPUT_REQUEST_KEY, listener)

    }

    private fun updateIncoming() {
        val textMessages = binding.editTextIncoming.text.toString()
        viewModel.updateTextIncoming(textMessages,currentIncoming.idIm)
    }

    private fun showAlertDialogBack() {

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

    private fun showAlertDialogDelete() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteIncoming(currentIncoming)
                    navigator().goBaseMenu()
                }
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_delete_title)
            .setMessage(R.string.alert_dialog_delete_text)
            .setPositiveButton(R.string.alert_dialog_action_yes, listener)
            .setNegativeButton(R.string.alert_dialog_action_no, listener)
            .create()
        dialog.show()
    }


    companion object {
        private const val ARG_NOTE_ID = "ARG_NOTE_ID"
        private const val ARG_INCOMING_ID = "ARG_INCOMING_ID"
        private const val ARG_INCOMING_DATA = "ARG_INCOMING_DATA"

        @JvmStatic private val KEY_FIRST_GOALS_REQUEST_KEY = "KEY_FIRST_GOALS_REQUEST_KEY"
        @JvmStatic private val KEY_INPUT_REQUEST_KEY = "KEY_INPUT_REQUEST_KEY"

        fun newInstance(incomingId: Int, noteId: Int,  currentDataIn: String): IncomingFragment {
            val fragment = IncomingFragment()
            fragment.arguments = bundleOf(ARG_INCOMING_ID to incomingId, ARG_NOTE_ID to noteId,ARG_INCOMING_DATA to currentDataIn)
            return fragment
        }
    }
}
