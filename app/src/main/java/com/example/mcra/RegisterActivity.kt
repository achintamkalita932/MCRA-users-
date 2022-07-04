package com.example.mcra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        val name: EditText = findViewById(R.id.etNameReg)
        val email: EditText = findViewById(R.id.etEmailReg)
        val password: EditText = findViewById(R.id.etPasswordReg)
        val submit: Button = findViewById(R.id.SubmitRegister)
        submit.setOnClickListener {
            if (name.text.toString().isNullOrEmpty() || email.text.toString()
                    .isNullOrEmpty() || password.text.toString().isNullOrEmpty()
            ) {
                Toast.makeText(applicationContext, "Fill Up the details.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(
                    email.text.toString().trim(),
                    password.text.toString().trim()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign Up is successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        saveDetails(
                            name.text.toString(),
                            email.text.toString(),
                            password.text.toString()
                        )
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveDetails(name: String, email: String, password: String) {
        db = FirebaseFirestore.getInstance().document("Users/Consumer")

        val uid: String = auth.currentUser?.uid.toString()

        val specification = HashMap<String, Any>()
        specification.put("Name", name)
        specification.put("Email", email)
        specification.put("Password", password)
        db.collection(uid).document("Information").set(specification).addOnSuccessListener {
            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Data Not Saved",Toast.LENGTH_SHORT).show()
        }
    }
}