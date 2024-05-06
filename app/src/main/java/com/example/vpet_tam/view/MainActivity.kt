package com.example.vpet_tam.view

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vpet_tam.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
       // navController.navigate(R.id.navigation_notifications)

        binding.btn.setOnClickListener {
            showPopupMenu(binding.btn)
        }
        */

    }

    /*fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.popup_menu, menu)
        setOnMenuItemClickListener { item ->
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            true
        }
        show()
    }*/
}