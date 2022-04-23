package com.example.trackinggoals.screens.tab.motivation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentMotivationBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.viewModelCreator


class MotivationFragment : Fragment() {

    private lateinit var binding: FragmentMotivationBinding
    private val viewModel by viewModelCreator { MotivationViewModel(Repositories.quoteRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadQuotes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMotivationBinding.inflate(inflater, container, false)
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