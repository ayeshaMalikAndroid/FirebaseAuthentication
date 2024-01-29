package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VehicleActivity : AppCompatActivity() {
    lateinit var edtOwnerName: EditText
    lateinit var edtVehicleBrand: EditText
    lateinit var edtVehicleRTO: EditText
    lateinit var edtVehicleNumber: EditText
    lateinit var btnSave: Button
    lateinit var btnGoToReadScreen: Button
    lateinit var btnUpdate :Button
    lateinit var btnDelete :Button
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        edtOwnerName = findViewById(R.id.edt_owner_name)
        edtVehicleBrand = findViewById(R.id.edt_vehicle_brand)
        edtVehicleRTO = findViewById(R.id.edt_vehicle_rto)
        edtVehicleNumber = findViewById(R.id.edt_vehicle_number)
        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete_data)
        btnGoToReadScreen = findViewById(R.id.btn_go_to_read_screen)
        btnUpdate = findViewById(R.id.btn_update)
        btnDelete.setOnClickListener {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }
        btnGoToReadScreen.setOnClickListener {
            val intent = Intent(this, ReadVehicleInfoActivity::class.java)
            startActivity(intent)
        }
        btnUpdate.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)
        }
        btnSave.setOnClickListener {
            val vehicleOwnerName = edtOwnerName.text
            val vehicleBrand = edtVehicleBrand.text
            val vehicleRTO = edtVehicleRTO.text
            val vehicleNumber = edtVehicleNumber.text
            databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
            val vehicleData = VehicleData(
                vehicleOwnerName.toString(),
                vehicleBrand.toString(), vehicleRTO.toString(), vehicleNumber.toString()
            )
            databaseReference.child(vehicleNumber.toString()).setValue(vehicleData)
                .addOnSuccessListener {
                    edtOwnerName.text.clear()
                    edtVehicleBrand.text.clear()
                    edtVehicleRTO.text.clear()
                    edtVehicleNumber.text.clear()
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ShowActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }
        }


    }
}