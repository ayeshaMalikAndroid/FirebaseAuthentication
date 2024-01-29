package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        val textView = findViewById<TextView>(R.id.name)
        val textViewEmail = findViewById<TextView>(R.id.tv_email)

        val auth = Firebase.auth
        val user = auth.currentUser


        if (user != null) {
            val userName = user.displayName
            val email = user.email
            val mobileNumber = user.phoneNumber
            "Welcome, $userName ".also { textView.text = it }
            textViewEmail.text = "$email"
            saveUserToDatabase(user.uid, userName, email, mobileNumber)
        } else {
            // Handle the case where the user is not signed in
        }



// Inside onCreate() method
        val sign_out_button = findViewById<Button>(R.id.logout_button)
        sign_out_button.setOnClickListener {
            signOutAndStartSignInActivity()
        }




    }

    private fun saveUserToDatabase(userId: String, userName: String?, email: String?, mobileNumber: String?) {
        val userMap = hashMapOf(
            "userId" to userId,
            "userName" to userName,
            "email" to email,
            "mobileNumber" to mobileNumber
        )

        databaseReference.child(userId).setValue(userMap)
    }
    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            // Optional: Update UI or show a message to the user
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}