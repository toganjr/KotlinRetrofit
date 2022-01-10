package com.example.kotlinretrofit

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.kotlinretrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        supportActionBar?.elevation = 0f
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.image_white)))

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)

        val btnDaftar = findViewById<Button>(R.id.btn_daftar)
        val tvTitle = findViewById<TextView>(R.id.title)


        btnDaftar.setOnClickListener {
            val message = tvTitle.text
            val intent = Intent(this, RecyclerListActivity::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }
    }
}
