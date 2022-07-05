package com.example.mcra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class LoggingComplainRA : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var complainArrayList: ArrayList<ComplainData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_complain_ra)

        userRecyclerView = findViewById(R.id.ComplainBookLogging)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        complainArrayList = arrayListOf<ComplainData>()
        getUserData()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Complains/Water Logging")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                complainArrayList.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val complain = userSnapshot.getValue(ComplainData::class.java)
                        complainArrayList.add(complain!!)
                    }

                    val mAdapter = ComplainAdapter(complainArrayList)
                    userRecyclerView.adapter = mAdapter
                    mAdapter.setOnItemClickListener(object : ComplainAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(
                                this@LoggingComplainRA,
                                ComplainDetailedActivity::class.java
                            )

                            intent.putExtra("category", complainArrayList[position].category)
                            intent.putExtra("user_ID", complainArrayList[position].uid)
                            intent.putExtra("complain", complainArrayList[position].complainR)
                            intent.putExtra("address", complainArrayList[position].addressR)
                            intent.putExtra("status", complainArrayList[position].Status)

                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}