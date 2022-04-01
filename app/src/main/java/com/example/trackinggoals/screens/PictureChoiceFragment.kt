package com.example.trackinggoals.screens


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.PictureChoiceFragmentBinding

import com.example.trackinggoals.navigator


typealias PictureChoiceFragmentListener = (requestKey: String, progress: String) -> Unit

class PictureChoiceFragment : Fragment() {
    private lateinit var binding: PictureChoiceFragmentBinding
    private lateinit var adapter: PictureAdapter

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_PICTURE_KEY)!!

    private val goalsId: Int
        get() = requireArguments().getInt(ARG_REQUEST_GOALS_ID)!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PictureChoiceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listPictureValues = (0..10).map {
            PictureValues(
                pictureId = it,
                path = IMAGES[it]
            )
        }.toMutableList()
        adapter = PictureAdapter(navigator() , object : PictureActionListener {
            override fun selectPicture(pictureValues: PictureValues) {
                parentFragmentManager.setFragmentResult(requestKey, bundleOf(
                    PictureChoiceFragment.KEY_PICTURE_RESPONSE to pictureValues.path)
                )
                navigator().showGoalsConstructor(goalsId)
            }
        })
        adapter.picturesValues = listPictureValues
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPicture.layoutManager = layoutManager
        binding.recyclerViewPicture.adapter = adapter

    }

    companion object {
        private val IMAGES = listOf<String>(
            "https://images.unsplash.com/photo-1601278840447-9af5ac4ed157?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=764&q=80",
            "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1565623833408-d77e39b88af6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1632&q=80",
            "https://images.unsplash.com/photo-1581009137042-c552e485697a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1571388208497-71bedc66e932?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
            "https://images.unsplash.com/photo-1524307751606-4f70b5303caa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1564403256236-8f6929897a47?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1051&q=80",
            "https://images.unsplash.com/photo-1592921710055-bbf038e8f4ab?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
            "https://images.unsplash.com/photo-1512820790803-83ca734da794?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1198&q=80",
            "https://images.unsplash.com/photo-1543109740-4bdb38fda756?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://images.unsplash.com/photo-1584346133934-a3afd2a33c4c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"
        )

        @JvmStatic
        private val TAG = PictureChoiceFragment::class.java.simpleName

        @JvmStatic
        private val KEY_PICTURE_RESPONSE = "KEY_PICTURE_RESPONSE"

        @JvmStatic
        private val ARG_PICTURE = "ARG_PICTURE"

        @JvmStatic
        private val ARG_REQUEST_GOALS_ID = "ARG_REQUEST_GOALS_ID"

        @JvmStatic
        private val ARG_REQUEST_PICTURE_KEY = "ARG_REQUEST_PICTURE_KEY"
        @JvmStatic
        val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun newInstance(requestKey: String,goalsId: Int): PictureChoiceFragment {
            val fragment = PictureChoiceFragment()
            fragment.arguments = bundleOf(ARG_REQUEST_PICTURE_KEY to requestKey,ARG_REQUEST_GOALS_ID to goalsId)
            return fragment
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            requestKey: String,
            listener: PictureChoiceFragmentListener
        ) {
            manager.setFragmentResultListener(
                requestKey,
                lifecycleOwner,
                FragmentResultListener { key, result ->
                    listener.invoke(key, result.getString(KEY_PICTURE_RESPONSE).toString())
                })
        }
    }
}
