package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ShowActivity : AppCompatActivity() {
    lateinit var btnRead :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        btnRead = findViewById(R.id.btn_show)
        btnRead.setOnClickListener {

            val intent = Intent(this,ReadVehicleInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}