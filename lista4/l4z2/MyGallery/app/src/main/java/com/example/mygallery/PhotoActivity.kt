package com.example.mygallery

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.example.mygallery.databinding.ActivityMainBinding
import com.example.mygallery.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityPhotoBinding

    lateinit var tvDescription: TextView
    lateinit var etDescription: EditText
    lateinit var ratingBar: RatingBar
    lateinit var imageView: ImageView
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }


    override fun onPause() {
        super.onPause()
        println("Akt photo co tu mamy")

        var extr = intent.extras
        var path = extr?.getString("path")
        var rating = extr?.getFloat( "rating")
        var description = extr?.getString("description")


        println("in Intent fragment")
        println("path $path ")
        println("rating $rating ")
        println("description $description ")
        println("Activity.RESULT_OK ${Activity.RESULT_OK} ")
        println("activity ${this} ")
        println(intent)
        setResult(Activity.RESULT_OK, intent)
    }
}