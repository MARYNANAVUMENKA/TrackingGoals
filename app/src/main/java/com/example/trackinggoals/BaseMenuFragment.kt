package com.example.trackinggoals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentBaseMenuBinding

class BaseMenuFragment : Fragment() {
    private lateinit var binding: FragmentBaseMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseMenuBinding.inflate(inflater, container, false)
        childFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerMenu, NoteListFragment.newInstance())
            .commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.selectedItemId = R.id.menuDiary
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuDiary -> {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerMenu, NoteListFragment.newInstance())
                        .commit()
                }
                R.id.menuGoals -> {}
                R.id.menuMenu -> {}
            }
            true
        }
    }

    companion object {
        fun newInstance(): BaseMenuFragment {
            return BaseMenuFragment()
        }
    }

}
