package com.example.trackinggoals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trackinggoals.databinding.ActivityMainBinding
import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.screens.goals.constructor.GoalsConstructorFragment
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.screens.incoming.IncomingFragment
import com.example.trackinggoals.screens.menu.BaseMenuFragment
import java.util.*

class MainActivity : AppCompatActivity(),Navigator {

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

    override fun showIncoming(incoming: Incoming) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                IncomingFragment.newInstance(incoming.idIm, incoming.idNote, incoming.currentDataIn)
            )
            .commit()
    }

    override fun showIncoming(noteIncoming: NoteIncoming) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                IncomingFragment.newInstance(UUID.randomUUID().hashCode(),noteIncoming.note.id,noteIncoming.note.currentData)
            )
            .commit()
    }
    override fun showNewNoteIncoming(noteIncoming: NoteIncoming) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                IncomingFragment.newInstance(1,noteIncoming.note.id,noteIncoming.note.currentData)
            )
            .commit()
    }

    override fun goBaseMenu() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, BaseMenuFragment.newInstance())
            .commit()
    }

    override fun showGoalsConstructor(goalsId:Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, GoalsConstructorFragment.newInstance(goalsId))
            .commit()
    }

        override fun goBack() {
        onBackPressed()
    }


}
