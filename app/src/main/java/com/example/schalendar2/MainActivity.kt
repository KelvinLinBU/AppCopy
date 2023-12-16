package com.example.schalendar2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        val letsgobutton = findViewById<Button>(R.id.letsgobutton)
        letsgobutton.setOnClickListener{
            val i = Intent(this, SignUpPage::class.java)
            this.startActivity(i)
        }
    }
}
