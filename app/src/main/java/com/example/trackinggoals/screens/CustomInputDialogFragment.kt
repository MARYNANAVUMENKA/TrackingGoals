package com.example.trackinggoals.screens

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.InputDialogBinding


typealias CustomInputDialogListener = (requestKey: String, progress: String) -> Unit

class CustomInputDialogFragment : DialogFragment() {

//    private val quantity: Int
//        get() = requireArguments().getInt(ARG_QUANTITY)

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY)!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = InputDialogBinding.inflate(layoutInflater)
//        dialogBinding.progressInputEditText.setText(progress.toString())

        val dialog = AlertDialog.Builder(requireContext())
                .setTitle(R.string.title_custom_fragment)
                .setView(dialogBinding.root)
                .setPositiveButton(R.string.custom_fragment_action_confirm, null)
                .create()

        dialog.setOnShowListener {
            dialogBinding.progressInputEditText.requestFocus()
            showKeyboard(dialogBinding.progressInputEditText)

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.progressInputEditText.text.toString()
                if (enteredText.isBlank()&&enteredText==null) {
                    dialogBinding.progressInputEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }
                val progress = enteredText
                if (!progress.contains("-")&&!progress.contains("+")) {
                    dialogBinding.progressInputEditText.error = getString(R.string.invalid_value)
                    return@setOnClickListener
                }

                parentFragmentManager.setFragmentResult(requestKey, bundleOf(KEY_PROGRESS_RESPONSE to progress))
                dismiss()
            }
        }
        dialog.setOnDismissListener { hideKeyboard(dialogBinding.progressInputEditText) }

        return dialog
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

    companion object {
        @JvmStatic private val TAG = CustomInputDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_PROGRESS_RESPONSE = "KEY_PROGRESS_RESPONSE"
        @JvmStatic private val ARG_QUANTITY = "ARG_QUANTITY"
        @JvmStatic private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"



        fun show(manager: FragmentManager, requestKey: String) {
            val dialogFragment = CustomInputDialogFragment()
            dialogFragment.arguments = bundleOf(
//                    ARG_QUANTITY to quantity,
                    ARG_REQUEST_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, requestKey: String, listener: CustomInputDialogListener) {
            manager.setFragmentResultListener(requestKey, lifecycleOwner, FragmentResultListener { key, result ->
                result.getString(KEY_PROGRESS_RESPONSE)?.let { listener.invoke(key, it) }
            })
        }

    }
}