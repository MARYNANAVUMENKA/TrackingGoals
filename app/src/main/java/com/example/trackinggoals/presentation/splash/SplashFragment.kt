package com.example.trackinggoals.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentSplashBinding
import com.example.trackinggoals.presentation.main.MainActivity
import com.example.trackinggoals.presentation.main.MainActivityArgs

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private  var _binding: FragmentSplashBinding?=null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSplashBinding.bind(view)
        renderAnimations()
        launchMainScreen(true)
    }

    private fun launchMainScreen(isSignedIn: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val args = MainActivityArgs(isSignedIn)
        intent.putExtras(args.toBundle())
        startActivity(intent)
    }

    private fun renderAnimations() {
        binding.loadingIndicator.alpha = 0f
        binding.loadingIndicator.animate()
            .alpha(0.7f)
            .setDuration(1000)
            .start()

        binding.pleaseWaitTextView.alpha = 0f
        binding.pleaseWaitTextView.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()
    }
}