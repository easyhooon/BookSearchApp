package com.kenshi.booksearchapp.presentation.screen

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kenshi.booksearchapp.R
import com.kenshi.booksearchapp.databinding.ActivityMainBinding
import com.kenshi.booksearchapp.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    // lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    // datastore 의 singleton 객체
//    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)
//    private val workManager = WorkManager.getInstance(application)

//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        /*
//        setupBottomNavigationView()
//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
//        }*/
//        setupNavigation()
//
////        val database = BookSearchDatabase.getInstance(this)
////        val bookSearchRepository = BookSearchRepositoryImpl(database, dataStore)
////        val factory = BookSearchViewModelProviderFactory(bookSearchRepository, workManager, this)
////        bookSearchViewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]
//    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun preload() {
        installSplashScreen()
    }

    override fun init() {
        setupNavigation()
    }


    private fun setupNavigation() {
        val host =
            supportFragmentManager.findFragmentById(R.id.bookssearch_nav_host_fragment) as NavHostFragment
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            navController.graph
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        // Top 레벨의 destination 을 하나씩 지정해줌
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_search, R.id.fragment_favorite, R.id.fragment_settings
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//      Navigation 도입으로 필요가 없어짐
//    private fun setupBottomNavigationView() {
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.fragment_search -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SearchFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_favorite -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, FavoriteFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_settings -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SettingsFragment())
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
//    }
}