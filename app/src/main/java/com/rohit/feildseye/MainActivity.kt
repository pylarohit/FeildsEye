package com.rohit.feildseye

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.rohit.feildseye.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.host) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> binding?.tittleToolbar?.setText("Fields Eye")
                R.id.agriloansFragment -> binding?.tittleToolbar?.setText("Agri Loans")
                R.id.agriguideFragment -> binding?.tittleToolbar?.setText("Agri Guide")
                R.id.ecomProductFragment -> binding?.tittleToolbar?.setText("Agri Products")
                R.id.moistureFragment -> binding?.tittleToolbar?.setText("Soil Moisture")
                R.id.waterPumpFragment -> binding?.tittleToolbar?.setText("Water Pump")
                R.id.birdsAlarmFragment -> binding?.tittleToolbar?.setText("Birds Alarm")
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.popBackStack()
    }
}
