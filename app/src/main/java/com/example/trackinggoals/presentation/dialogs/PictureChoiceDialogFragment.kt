package com.example.trackinggoals.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.trackinggoals.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PictureChoiceDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = resources.getStringArray(R.array.picture)
        return MaterialAlertDialogBuilder(requireContext())
            .setCancelable(true)
            .setItems(items) { _, which ->
                val action = items[which]
                var chosenAction = ""
                when (action) {
                    items[0] -> {
                        chosenAction = KEY_ACTION_RESPONSE_CHOOSE
                    }
                    items[1] -> {
                        chosenAction = KEY_ACTION_RESPONSE_LOAD
                    }
                }
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(KEY_ACTION_RESPONSE to chosenAction)
                )
                dismiss()
            }
            .show()
    }

    companion object {
        @JvmStatic
        private val TAG = PictureChoiceDialogFragment::class.java.simpleName

        @JvmStatic
        private val KEY_ACTION_RESPONSE = "KEY_ACTION_RESPONSE"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_CHOOSE = "KEY_ACTION_RESPONSE_CHOOSE"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_LOAD = "KEY_ACTION_RESPONSE_LOAD"

        @JvmStatic
        val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager) {
            val dialogFragment = PictureChoiceDialogFragment()
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (String) -> Unit
        ) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, { _, result ->
                result.getString(KEY_ACTION_RESPONSE)?.let { listener.invoke(it) }
            })
        }
    }
}