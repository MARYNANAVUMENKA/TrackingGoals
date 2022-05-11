package com.example.trackinggoals.screens.tab.motivation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentMotivationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MotivationFragment : Fragment() {

    private  var _binding: FragmentMotivationBinding?=null
    private val binding get() = _binding!!
    private val viewModel by viewModel<MotivationViewModel> { parametersOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadQuotes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMotivationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.quoteText.observe(viewLifecycleOwner) {
            when(it){
                ""-> Toast.makeText(requireContext(), R.string.error_network, Toast.LENGTH_SHORT)
                    .show()
            }
            binding.textViewQuotes.text = it
        }
        viewModel.author.observe(viewLifecycleOwner) {
            binding.textViewAuthor.text = it
        }
    }

}