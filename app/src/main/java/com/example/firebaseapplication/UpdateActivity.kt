package com.example.firebaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var edtOwnerName: EditText
    private lateinit var edtVehicleRTO: EditText
    private lateinit var edtVehicleBrand: EditText
    private lateinit var edtVehicleNumber: EditText
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        edtOwnerName = findViewById(R.id.edt_owner_name_update)
        edtVehicleBrand = findViewById(R.id.edt_vehicle_brand_update)
        edtVehicleNumber = findViewById(R.id.edt_vehicle_number_update)
        edtVehicleRTO = findViewById(R.id.edt_vehicle_rto_update)
        btnUpdate = findViewById(R.id.btn_uppdate)
        btnUpdate.setOnClickListener {
            val updateVehNumber = edtVehicleNumber.text
            val updateOwner = edtOwnerName.text
            val updateVehBrand = edtVehicleBrand.text
            val updateVehRTO = edtVehicleRTO.text
            updateData(
                updateVehNumber.toString(),
                updateOwner.toString(),
                updateVehBrand.toString(),
                updateVehRTO.toString()
            )

        }
    }

    private fun updateData(
        vehicleNumber: String,
        ownerName: String,
        vehicleBrand: String,
        vehicleRTO: String
    ) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        val vehicleData = mapOf<String, String>(
            "ownerName" to ownerName,
            "vehicleBranch" to vehicleBrand,
            "vehicleRTO" to vehicleRTO
        )
        databaseReference.child(vehicleNumber).updateChildren(vehicleData).addOnSuccessListener {
            edtOwnerName.text.clear()
            edtVehicleRTO.text.clear()
            edtVehicleBrand.text.clear()
            edtVehicleNumber.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to update", Toast.LENGTH_SHORT).show()

        }
    }
}