package com.example.kinopoisk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kinopoisk.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavView: BottomNavigationView = binding.btmNav

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment!!.findNavController()

        bottomNavView.setupWithNavController(navController)


        /*   val appBarConfiguration = AppBarConfiguration(
               setOf(
                   R.id.navigation_home,
                   R.id.navigation_favourite,
                   R.id.navigation_shop,
                   R.id.navigation_profile
               )
           )*/
/*
        bottomNavView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.navigation_home || nd.id == R.id.navigation_favourite
                ||nd.id == R.id.navigation_profile ||nd.id == R.id.navigation_shop) {
                bottomNavView.visibility = View.VISIBLE
            } else {
                bottomNavView.visibility = View.INVISIBLE
            }
        }*/
    }

    private fun setFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}


