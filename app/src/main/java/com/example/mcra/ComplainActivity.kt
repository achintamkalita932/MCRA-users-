package com.example.mcra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ComplainActivity : AppCompatActivity() {

    private lateinit var category: String
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DocumentReference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance().document("Users/Consumer")
        database = Firebase.database.reference

        val uid = auth.currentUser?.uid.toString()

        val complainID: Int = (100..999).random()

        val complain = findViewById<EditText>(R.id.etComplain)
        val address = findViewById<EditText>(R.id.etAddress)
        val submit = findViewById<Button>(R.id.submitComplain)
        submit.setOnClickListener {
            val complainR = complain.text.toString().trim()
            val addressR = address.text.toString().trim()

            val specification = HashMap<String, Any>()
            specification.put("Complain_ID", complainID)
            specification.put("User_ID", uid)
            specification.put("Complain", complainR)
            specification.put("Address", addressR)
            specification.put("Category", category)

            db.collection("$uid").document("Complain").collection("$complainID")
                .document("Description").set(specification)
                .addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Your Complain has been registered.",
                        Toast.LENGTH_SHORT
                    ).show()
                    saveComplain(complainID, uid, complainR, addressR, category)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveComplain(id: Int, uid: String, complainR: String, addressR: String, category: String) {
        val complainInformation = ComplainData(id, uid, complainR, addressR, category)

        database.child("Complains").child(category).child(id.toString()).setValue(complainInformation)
    }

    fun waterCategory(view: View) {
        category = "Water Supply"
    }

    fun loggingCategory(view: View) {
        category = "Water Logging"
    }

    fun GarbageCategory(view: View) {
        category = "Garbage"
    }

    fun DrainageCategory(view: View) {
        category = "Drainage"
    }
}