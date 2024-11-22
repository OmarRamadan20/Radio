package com.example.radio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val radioFragment = RadioFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_cotainer, radioFragment)
            .commit()

    }
}