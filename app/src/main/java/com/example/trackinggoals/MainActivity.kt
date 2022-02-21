package com.example.trackinggoals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.trackinggoals.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), Navigator {

//    private val viewModel by viewModelCreator { MainActivityViewModel(Repositories.accountsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
//        setSupportActionBar(binding.toolbar)




        if (savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, NoteListFragment.newInstance())
                    .commit()
            }
            binding.bottomNavigationView.selectedItemId = R.id.menuDiary
            binding.bottomNavigationView.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menuDiary -> {
                        supportFragmentManager
                            .beginTransaction()
                            .add(R.id.fragmentContainer, NoteListFragment.newInstance())
                            .commit()
                    }
                    R.id.menuGoals -> {}
                    R.id.menuMenu -> {}
                }
                true
            }
        }

    override fun showNewNote(note: Note) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragmentContainer,
                IncomingFragment.newInstance(note.id, note.currentData)
            )
            .commit()
    }


    override fun goBack() {
        onBackPressed()
    }


}
