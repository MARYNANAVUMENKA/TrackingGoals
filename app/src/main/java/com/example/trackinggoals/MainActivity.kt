package com.example.trackinggoals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trackinggoals.databinding.ActivityMainBinding
import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.screens.GoalsStepFirstFragment
import com.example.trackinggoals.model.NoteWithIncoming
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.screens.BaseMenuFragment
import com.example.trackinggoals.screens.GoalsStepSecondFragment
import com.example.trackinggoals.screens.IncomingFragment

class MainActivity : AppCompatActivity(),Navigator {

//    private val viewModel by viewModelCreator { MainActivityViewModel(Repositories.accountsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }


        if (savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, BaseMenuFragment.newInstance())
                    .commit()
            }
        }

    override fun showNewNote(noteWithIncoming: NoteWithIncoming) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                IncomingFragment.newInstance(noteWithIncoming.noteDbEntity.id, noteWithIncoming.noteDbEntity.currentData)
            )
            .commit()
    }


    override fun goBaseMenu() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, BaseMenuFragment.newInstance())
            .commit()
    }

    override fun showGoalsStepFirst(goalsId:Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, GoalsStepFirstFragment.newInstance(goalsId))
            .commit()
    }

    override fun showGoalsStepSecond(goalsId: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, GoalsStepSecondFragment.newInstance(goalsId))
            .commit()
    }
    override fun goBack() {
        onBackPressed()
    }


}
