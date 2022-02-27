package com.example.trackinggoals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trackinggoals.databinding.ActivityMainBinding

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


    override fun goBack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, BaseMenuFragment.newInstance())
            .commit()
    }
}
