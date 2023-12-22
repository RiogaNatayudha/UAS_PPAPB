package com.example.ppapb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ppapb.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ...

        val detailTitle = binding.tvJudulFilm
        val detailAuthor = binding.tvPembuatDanRating
        val detailGenre = binding.tvGenre
        val detailStoryline = binding.tvStoryline
        val detailDescription = binding.tvDeskripsi

        // Mengambil nilai dari intent
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val genre = intent.getStringExtra("genre")
        val storyline = intent.getStringExtra("storyline")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imgUrl")

        // Menampilkan data ke UI
        detailTitle.text = title
        detailAuthor.text = author
        detailGenre.text = genre
        detailStoryline.text = storyline
        detailDescription.text = description

        // Menampilkan gambar menggunakan Glide
        Glide.with(this@DetailActivity)
            .load(imageUrl)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.ivFilm)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this@DetailActivity, MainActivity::class.java))
        }
    }
}