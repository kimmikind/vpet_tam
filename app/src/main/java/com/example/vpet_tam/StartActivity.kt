package com.example.vpet_tam

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class StartActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.starting_activity)

        val play_btn = findViewById(R.id.play_button) as ImageButton

        play_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}