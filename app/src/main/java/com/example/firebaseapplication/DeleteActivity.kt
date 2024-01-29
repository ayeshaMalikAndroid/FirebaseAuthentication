package com.example.firebaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteActivity : AppCompatActivity() {
    lateinit var btnDel: Button
    lateinit var edtVehNumber :EditText
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        btnDel = findViewById(R.id.button_delete)
        edtVehNumber = findViewById(R.id.ed_vehicle_number_delete)
        btnDel.setOnClickListener {
            val vehNumber = edtVehNumber.text
            if(vehNumber.isNotEmpty()){
                deleteData(vehNumber.toString())
            }else{
                Toast.makeText(this, "Please enter vehicle number ", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun deleteData(vehicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Deleted ", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to Delete ", Toast.LENGTH_SHORT).show()

        }
    }
}