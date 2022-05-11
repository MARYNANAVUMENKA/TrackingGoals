package com.example.trackinggoals.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.trackinggoals.R
import com.example.trackinggoals.workmanager.RefreshDataWorker
import com.example.trackinggoals.screens.tab.TabsFragment
import com.example.trackinggoals.databinding.ActivityMainBinding
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    private val topLevelDestinations = setOf(getTabsDestination())

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val navController = getRootNavController()
        prepareRootNavController(navController)

        onNavControllerActivated(navController)

        val periodicConstraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorker = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            12,
            TimeUnit.HOURS
        )
            .addTag("PeriodicWorker")
            .setInitialDelay(25, TimeUnit.MINUTES)
            .setConstraints(periodicConstraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorker
        )

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (isStartDestination(navController?.currentDestination)) {
            super.onBackPressed()
        } else {
            navController?.popBackStack()
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()

    private fun prepareRootNavController(navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(getTabsDestination())
        navController.graph = graph
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController = navController
    }

    private fun getRootNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun getMainNavigationGraphId(): Int = R.navigation.main_graph
    private fun getTabsDestination(): Int = R.id.tabsFragment
}





