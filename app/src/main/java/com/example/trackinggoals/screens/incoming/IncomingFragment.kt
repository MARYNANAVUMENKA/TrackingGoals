package com.example.trackinggoals.screens.incoming

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentIncomingBinding
import com.example.trackinggoals.findTopNavController
import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.screens.dialogs.CustomInputDialogFragment
import com.example.trackinggoals.screens.dialogs.CustomInputDialogListener
import com.example.trackinggoals.screens.dialogs.GoalsBottomSheetDialogFragment
import com.example.trackinggoals.screens.dialogs.GoalsBottomSheetDialogFragmentListener
import com.example.trackinggoals.viewModelCreator
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class IncomingFragment : Fragment() {
    private lateinit var binding: FragmentIncomingBinding
    private val viewModel by viewModelCreator { IncomingViewModel(Repositories.incomingRepository) }
    private lateinit var currentIncoming: Incoming
    private var textIncoming = ""

    private val args by navArgs<IncomingFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadIncoming(getIncomingId(), getNoteId(), getCurrentDataIn())
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

        if (binding.editTextIncoming.isFocused) {
            showKeyboard(binding.editTextIncoming)
        }
        setupGoalsBottomSheetDialogFragmentListeners()
        setupCustomInputDialogFragmentListeners()

        binding.editTextIncoming.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.editTextIncoming.text.toString().isNotEmpty()) {
                    binding.imageViewDone.setImageResource(R.drawable.ic_done_focused)
                    binding.imageViewDone.isFocusable = true
                } else {
                    binding.imageViewDone.setImageResource(R.drawable.ic_done)
                    binding.imageViewDone.isFocusable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.incoming.observe(viewLifecycleOwner) {
            currentIncoming = it
        }
        viewModel.currentData.observe(viewLifecycleOwner) {
            binding.textViewData.text = it
        }
        viewModel.textMessage.observe(viewLifecycleOwner) {
            binding.editTextIncoming.setText(it)
            textIncoming = it
        }
        viewModel.textGoals.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.cardInner.isClickable = false
            }
            binding.textViewIncomingTextGoals.text = it
        }
        viewModel.quantity.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.cardInner.isClickable = false
            }
            binding.textViewIncomingQuantityGoals.text = it
        }

        binding.imageViewBack.setOnClickListener {
            hideKeyboard(binding.imageViewBack)
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    findTopNavController().popBackStack()
                }
            }
        }
        binding.imageViewDone.setOnClickListener {
            hideKeyboard(binding.imageViewDone)
            if (binding.imageViewDone.isFocusable) {
                updateIncoming()
                findTopNavController().popBackStack()
            } else {
                showAlertDialogBack()
            }
        }

        binding.imageViewDelete.setOnClickListener {
            hideKeyboard(binding.imageViewDelete)
            showAlertDialogDelete()
        }

        binding.cardInner.setOnClickListener {
            viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
                showGoalsBottomSheetDialogFragment(KEY_FIRST_GOALS_REQUEST_KEY, it)
            }
        }
    }


    private fun getIncomingId(): Int = args.incomingId

    private fun getNoteId(): Int = args.noteId

    private fun getCurrentDataIn(): String = args.currentDataIn

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showGoalsBottomSheetDialogFragment(
        requestKey: String,
        listGoals: ArrayList<String>
    ) {
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
            viewModel.selectedGoals.observe(viewLifecycleOwner) {
                binding.textViewIncomingTextGoals.text = it.textGoals
            }
        }
        GoalsBottomSheetDialogFragment.setupListener(
            parentFragmentManager,
            this,
            KEY_FIRST_GOALS_REQUEST_KEY,
            listener
        )

    }

    private fun showCustomInputDialogFragment(requestKey: String) {
        CustomInputDialogFragment.show(parentFragmentManager, requestKey)
    }

    private fun setupCustomInputDialogFragmentListeners() {
        val listener: CustomInputDialogListener = { requestKey, progress ->
            when (requestKey) {
                KEY_INPUT_REQUEST_KEY -> {
                    binding.textViewIncomingQuantityGoals.text = progress
                }
            }
        }
        CustomInputDialogFragment.setupListener(
            parentFragmentManager,
            this,
            KEY_INPUT_REQUEST_KEY,
            listener
        )

    }

    private fun updateIncoming() {
        val textMessages = binding.editTextIncoming.text.toString()
        viewModel.updateTextIncoming(textMessages, currentIncoming.idIm)

        val textGoals = binding.textViewIncomingTextGoals.text.toString()
        val quantity = binding.textViewIncomingQuantityGoals.text.toString()

        if (binding.textViewIncomingTextGoals.toString().isNotEmpty()) {
            viewModel.updateTextGoals(textGoals, currentIncoming.idIm)
        }
        if (binding.textViewIncomingQuantityGoals.toString().isNotEmpty()) {
            viewModel.updateQuantity(quantity, currentIncoming.idIm)
        }
        if (binding.textViewIncomingTextGoals.toString()
                .isNotEmpty() && binding.textViewIncomingQuantityGoals.toString().isNotEmpty()
        ) {
            viewModel.selectedGoals.observe(viewLifecycleOwner) {
                viewModel.updateProgress(
                    quantity,
                    it.id,
                    resources.getString(R.string.text_new_result)
                )
            }
        }
    }


    private fun showAlertDialogBack() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    if (textIncoming == "") {
                        viewModel.deleteIncoming(currentIncoming)
                        findTopNavController().popBackStack()
                    } else {
                        findTopNavController().popBackStack()
                    }
//                    navigateToNoteList()
                }
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = MaterialAlertDialogBuilder(requireContext())
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
                    findTopNavController().popBackStack()
//                    findTopNavController().navigate(R.id.tabsFragment, null, navOptions {
//                        popUpTo(R.id.tabsFragment) {
//                            inclusive = true
//                        }
//                    })
//                    navigateToNoteList()
                }
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_delete_title)
            .setMessage(R.string.alert_dialog_delete_text)
            .setPositiveButton(R.string.alert_dialog_action_yes, listener)
            .setNegativeButton(R.string.alert_dialog_action_no, listener)
            .create()
        dialog.show()
    }

    companion object {

        @JvmStatic
        private val KEY_FIRST_GOALS_REQUEST_KEY = "KEY_FIRST_GOALS_REQUEST_KEY"

        @JvmStatic
        private val KEY_INPUT_REQUEST_KEY = "KEY_INPUT_REQUEST_KEY"

    }
}
