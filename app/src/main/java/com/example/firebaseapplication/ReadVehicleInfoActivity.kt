package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadVehicleInfoActivity : AppCompatActivity() {
    lateinit var btnSearch: Button
    lateinit var edtSearch: EditText
   // lateinit var btnUpdate: Button
    lateinit var tvReadOwnerName: TextView
    lateinit var tvReadVehicleRTO: TextView
    lateinit var tvReadVehicleBrand: TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_vehicle_info)

        btnSearch = findViewById(R.id.btn_search)
        edtSearch = findViewById(R.id.edt_search)
        tvReadOwnerName = findViewById(R.id.tv_set_owner_name)
        tvReadVehicleBrand = findViewById(R.id.tv_set_brand)
        tvReadVehicleRTO = findViewById(R.id.tv_set_rto)
//        btnUpdate.setOnClickListener {
//            val intent = Intent(this, UpdateActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        btnSearch.setOnClickListener {
            val searchVehicleNumber: String = edtSearch.text.toString()

            if (searchVehicleNumber.isNotEmpty()) {
                read(searchVehicleNumber)
            } else {
                Toast.makeText(this, "Please enter the vehicle number.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun read(vehicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).get().addOnSuccessListener {
            if (it.exists()) {
                val ownerName = it.child("ownerName").value
                val vehicleBrand = it.child("vehicleBrand").value
                val vehicleRTO = it.child("vehicleRTO").value
                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                edtSearch.text.clear()
                tvReadOwnerName.text = ownerName.toString()
                tvReadVehicleRTO.text = vehicleRTO.toString()
                tvReadVehicleBrand.text = vehicleBrand.toString()

            } else {
                Toast.makeText(this, " This vehicle number does not exits.", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, " Something went wrong.", Toast.LENGTH_SHORT).show()
        }
    }
}