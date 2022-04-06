package com.example.trackinggoals.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentBaseMenuBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.screens.goals.list.achieved.GoalsListAchievedFragment
import com.example.trackinggoals.screens.goals.list.active.GoalsListActiveFragment
import com.example.trackinggoals.screens.goals.start.GoalsStartFragment
import com.example.trackinggoals.screens.note.NoteListFragment
import com.example.trackinggoals.viewModelCreator

class BaseMenuFragment : Fragment() {
    private lateinit var binding: FragmentBaseMenuBinding

    private val viewModel by viewModelCreator { BaseMenuViewModel(Repositories.goalsRepository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseMenuBinding.inflate(inflater, container, false)
//        viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
//            if (it.isEmpty()) {
//                childFragmentManager
//                    .beginTransaction()
//                    .replace(
//                        R.id.fragmentContainerMenu,
//                        GoalsStartFragment.newInstance()
//                    )
//                    .commit()
//            } else {
//                childFragmentManager
//                    .beginTransaction()
//                    .replace(
//                        R.id.fragmentContainerMenu,
//                        GoalsListActiveFragment.newInstance()
//                    )
//                    .commit()
//            }
//        }


            childFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerMenu, NoteListFragment.newInstance())
                .commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.selectedItemId=R.id.menuGoals
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuDiary -> {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerMenu, NoteListFragment.newInstance())
                        .commit()
                }
                R.id.menuGoals -> {
                    viewModel.loadListGoals()
                    viewModel.listGoalsLiveData.observe(viewLifecycleOwner) {
                        if (it.isEmpty()) {
                            childFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.fragmentContainerMenu,
                                    GoalsStartFragment.newInstance()
                                )
                                .commit()
                        } else {
                            childFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.fragmentContainerMenu,
                                    GoalsListActiveFragment.newInstance()
                                )
                                .commit()
                        }
                    }

                }
                R.id.menuMenu -> {
                    childFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainerMenu,
                            GoalsListAchievedFragment.newInstance()
                        )
                        .commit()
                }
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
