package com.example.mcra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ComplainR -> {
                val intent = Intent(applicationContext, ComplainActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun WaterComplainRegistered(view: View) {
        val intent = Intent(applicationContext, WaterSupplyComplainRA::class.java)
        startActivity(intent)
    }

    fun LoggingComplainRegistered(view: View) {
        val intent = Intent(applicationContext, LoggingComplainRA::class.java)
        startActivity(intent)
    }
    fun GarbageComplainRegistered(view: View) {
        val intent = Intent(applicationContext, GarbageComplainRA::class.java)
        startActivity(intent)
    }
    fun DrainageComplainRegistered(view: View) {
        val intent = Intent(applicationContext, DrainageComplainRA::class.java)
        startActivity(intent)
    }
}