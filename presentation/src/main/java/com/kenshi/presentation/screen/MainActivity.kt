package com.kenshi.presentation.screen

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kenshi.presentation.R
import com.kenshi.presentation.base.BaseActivity
import com.kenshi.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun preload() {
        installSplashScreen()
    }

    override fun init() {
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.bookssearch_nav_host_fragment) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            navController.graph
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        // Top 레벨의 destination 을 하나씩 지정해줌
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_search_books, R.id.fragment_favorite_books, R.id.fragment_settings
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
