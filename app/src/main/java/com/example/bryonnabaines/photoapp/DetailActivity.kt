package com.example.bryonnabaines.photoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.bryonnabaines.photoapp.models.Photo
import java.io.Serializable

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val imageView : ImageView = findViewById(R.id.imageView)
        val photo = intent.getSerializableExtra(PHOTO) as Photo?

        //if webURL is not empty do...
        //Glide will load in into the imageView
        photo?.webformatURL.let{
            Glide.with(this).load(photo?.webformatURL)
                    .into(imageView)
        }
        imageView.setOnClickListener{
            finish()
        }
    }
    companion object {
        val PHOTO = "PHOTO"
    }
}
