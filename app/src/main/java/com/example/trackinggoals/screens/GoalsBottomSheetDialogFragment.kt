package com.example.trackinggoals.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.BottomSheetGoalsFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias GoalsBottomSheetDialogFragmentListener = (requestKey: String, progress: String) -> Unit

class GoalsBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private lateinit var  binding:BottomSheetGoalsFragmentBinding

    private val listGoals: ArrayList<String>
        get() = requireArguments().getStringArrayList(ARG_GOALS) as ArrayList<String>

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_GOALS_KEY)!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetGoalsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("vvv",listGoals.toString())

//        val items = resources.getStringArray(R.array.unit)
//        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        val items= listGoals
        val adapter=ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.listView.setAdapter(adapter)
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            val textGoals = item
            parentFragmentManager.setFragmentResult(requestKey, bundleOf(KEY_GOALS_RESPONSE to textGoals))
            dismiss()
        }

    }



    companion object {
        @JvmStatic private val TAG = GoalsBottomSheetDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_GOALS_RESPONSE = "KEY_GOALS_RESPONSE"
        @JvmStatic private val ARG_GOALS = "ARG_GOALS"
        @JvmStatic private val ARG_REQUEST_GOALS_KEY = "ARG_REQUEST_GOALS_KEY"

        fun show(manager: FragmentManager, listGoals: ArrayList<String>, requestKey: String) {
            val dialogFragment = GoalsBottomSheetDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_GOALS to listGoals,
                ARG_REQUEST_GOALS_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, requestKey: String, listener: GoalsBottomSheetDialogFragmentListener) {
            manager.setFragmentResultListener(requestKey, lifecycleOwner, FragmentResultListener { key, result ->
                listener.invoke(key, result.getString(KEY_GOALS_RESPONSE).toString())
            })
        }
    }
}