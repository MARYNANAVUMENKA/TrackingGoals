package com.example.trackinggoals.screens

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.trackinggoals.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PictureChoiceDialogFragment : DialogFragment() {

//    private val volume: Int
//        get() = requireArguments().getInt(ARG_ACTION)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
//        val volumeTextItems = volumeItems.values
//            .map { getString(R.string.volume_description, it) }
//            .toTypedArray()

        val items = resources.getStringArray(R.array.picture)
        Log.i("iii",items.toString())

        return MaterialAlertDialogBuilder(requireContext())
            .setCancelable(true)
            .setItems(items) {_, which ->
                val action = items[which]
                var chosenAction=""
                when(action){
                    items[0]->{chosenAction = KEY_ACTION_RESPONSE_CHOOSE }
                    items[1] ->{chosenAction = KEY_ACTION_RESPONSE_LOAD}
                    items[2]->{chosenAction = KEY_ACTION_RESPONSE_CREATE}
                }
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_ACTION_RESPONSE to chosenAction))
                dismiss()
            }
            .show()
    }

    companion object {
        @JvmStatic private val TAG = PictureChoiceDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_ACTION_RESPONSE = "KEY_ACTION_RESPONSE"
        @JvmStatic private val KEY_ACTION_RESPONSE_CHOOSE = "KEY_ACTION_RESPONSE_CHOOSE"
        @JvmStatic private val KEY_ACTION_RESPONSE_LOAD = "KEY_ACTION_RESPONSE_LOAD"
        @JvmStatic private val KEY_ACTION_RESPONSE_CREATE = "KEY_ACTION_RESPONSE_CREATE"

        @JvmStatic private val ARG_ACTION = "ARG_ACTION"

        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager) {
            val dialogFragment = PictureChoiceDialogFragment()
//            dialogFragment.arguments = bundleOf(ARG_ACTION to action)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (String) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                result.getString(KEY_ACTION_RESPONSE)?.let { listener.invoke(it) }
            })
        }

    }

}