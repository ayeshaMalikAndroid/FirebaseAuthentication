package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {


    companion object {
        // constant is used to identify the request code for the Google Sign-In intent.
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var edtName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        databaseReference = FirebaseDatabase.getInstance().reference.child("person Info")

        // Initialize FirebaseApp (this might not be necessary, as Firebase is usually auto-initialized)
        FirebaseApp.initializeApp(this)
        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        //  databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        edtName = findViewById(R.id.edt_name)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        // Check if the user is already signed in
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }
        btnLogin.setOnClickListener {
            val name = edtName.text
            val password = edtPassword.text
            val data = saveUserToDatabase(name.toString(), password.toString())
            databaseReference.child(name.toString()).setValue(data).addOnSuccessListener {
                edtPassword.text.clear()
                edtPassword.text.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

            }
        }
        // Find the sign-in button in the layout and set a click listener
        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            // signIn()
        }
    }

    private fun saveUserToDatabase(userName: String?, passwrod: String?) {
        val userMap = hashMapOf(
            "userName" to userName,
            "password" to passwrod,
        )

        databaseReference.child(userName.toString()).setValue(userMap)
    }

    private fun saveUserToDatabase(name: String, password: String): UserData {
        // Create and return an instance of UserData
        return UserData(name, password)
    }

    data class UserData(val name: String, val password: String)
//    private fun signIn() {
//        showProgressBar()
//        //// Configure Google Sign-In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//
//            .requestEmail()
//            .build()
//        // Create a GoogleSignInClient with the configured options
//        val googleSignInClient = GoogleSignIn.getClient(this, gso)
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        hideProgressBar()
//        if (requestCode == RC_SIGN_IN) {
//            // Handle the result of the Google Sign-In intent
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Successfully obtained GoogleSignInAccount, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Handle failure of Google Sign-In
//
//                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
//
//    private fun showProgressBar() {
//        progressBar.visibility = View.VISIBLE
//    }
//
//    private fun hideProgressBar() {
//        progressBar.visibility = View.GONE
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        // Create a Firebase credential with the Google ID token
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        // Sign in to Firebase with the credential
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign-in success, update UI with the signed-in user's information
//                    val user = auth.currentUser
//                    //  saveUserToDatabase(user)
//                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT)
//                        .show()
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                } else {
//                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun saveUserToDatabase(user: FirebaseUser?) {
//        // Save user information to the Firebase Realtime Database
//        user?.let {
//            val userId = it.uid
//            val userName = it.displayName
//            val userEmail = it.email
//            val userPhone = "" // Add logic to retrieve the user's phone number if needed
//
//            val userMap = hashMapOf(
//                "userId" to userId,
//                "userName" to userName,
//                "userEmail" to userEmail,
//                "userPhone" to userPhone
//            )
//
//            databaseReference.child(userId).setValue(userMap)
//        }
//    }
}