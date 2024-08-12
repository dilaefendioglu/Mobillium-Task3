package com.dilaefendioglu.interntasks_3a

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.dilaefendioglu.interntasks_3a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.simpleFragment -> {
                    supportActionBar?.title = "Counter"

                }

                R.id.guessNumberFragment -> {
                    supportActionBar?.title = "Game"
                }
                R.id.detailFragment -> {
                    supportActionBar?.title = "Detail"
                }
                else -> {
                    supportActionBar?.title = "Intern Task - 3"
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}