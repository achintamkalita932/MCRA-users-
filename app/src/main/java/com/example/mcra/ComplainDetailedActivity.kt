package com.example.mcra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ComplainDetailedActivity : AppCompatActivity() {

    private lateinit var category: TextView
    private lateinit var user_id: TextView
    private lateinit var complain: TextView
    private lateinit var address: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain_detailed)

        initView()
        setValuesToViews()
    }

    private fun initView() {
        category = findViewById(R.id.categoryDetail)
        user_id = findViewById(R.id.user_idDetail)
        complain = findViewById(R.id.complainDetail)
        address = findViewById(R.id.addressDeatil)
    }

    private fun setValuesToViews(){
        category.text = intent.getStringExtra("category")
        user_id.text = intent.getStringExtra("user_ID")
        complain.text = intent.getStringExtra("complain")
        address.text = intent.getStringExtra("address")
    }
}