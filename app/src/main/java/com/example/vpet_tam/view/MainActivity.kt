package com.example.vpet_tam.view

import android.R.attr.name
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vpet_tam.databinding.ActivityMainBinding
import com.example.vpet_tam.genrandom.DataHelper.Companion.PREF
import com.example.vpet_tam.view.SettingsFragment.Companion.save_img


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sh = getSharedPreferences(PREF, MODE_PRIVATE)
        SettingsFragment.id_check = sh.getInt("id", 0)

    }

    // Fetch the stored data in onResume() Because this is what will be called when the app opens again
    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "act resume ${save_img}", Toast.LENGTH_SHORT).show()
        // Fetching the stored data from the SharedPreference
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        SettingsFragment.save_img = sh.getInt("img",0)
        SettingsFragment.id_check = sh.getInt("id", 0)
        sh.edit().remove("id").apply();
        sh.edit().remove("img").apply();
    }
    // Store the data in the SharedPreference in the onPause() method
    // When the user closes the application onPause() will be called and data will be stored
    override fun onPause() {
        super.onPause()
        Toast.makeText(this, " act pause ${save_img}", Toast.LENGTH_SHORT).show()
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putInt("img",SettingsFragment.save_img)
        myEdit.putInt("id", SettingsFragment.id_check)
        myEdit.apply()
    }
}