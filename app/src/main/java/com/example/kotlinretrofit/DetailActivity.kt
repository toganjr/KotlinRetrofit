package com.example.kotlinretrofit

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kotlinretrofit.databinding.ActivityDetailBinding
import com.example.kotlinretrofit.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        supportActionBar?.elevation = 0f
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.image_white)))

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)

        val image_link =intent.getStringExtra("image")
        val description =intent.getStringExtra("description")
        val title = intent.getStringExtra("title")

        Glide.with(binding.root)
            .load(image_link)
            .into(binding.ivDetail)
        binding.tvDescription.text = description
        binding.tvDetailtitle.text = title

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvDescription.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}