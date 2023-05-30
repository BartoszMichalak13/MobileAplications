package com.example.mygallery

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide

class RestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var tvDescription: TextView
    lateinit var etDescription: EditText
    lateinit var ratingBar: RatingBar
    lateinit var btnSave: Button
    var description: String? = null
    var rating: Float? = null
    var path: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_rest, container, false)
        ratingBar = view.findViewById(R.id.ratingBar)
        tvDescription = view.findViewById(R.id.textViewDescritpion)
        etDescription = view.findViewById(R.id.editTextdescription)
        btnSave = view.findViewById(R.id.btnSave)

        btnSave.setOnClickListener {

            tvDescription.text = etDescription.text
            description = tvDescription.text.toString()
            //        TODO( "Zapisuje zmiane przy obrocie, fragmenty, starRating przekazuje i zapisuje do sortowania, zapisuje na stale tj. nie znika ")
        }
        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, d ->
            rating = ratingBar.rating

        }
        val extras = requireActivity().intent.extras

        if (extras != null) {
            path = extras.getString("address")
            description = extras.getString("description")
            rating = extras.getFloat("rating")

            println("description: $description")
            println("rating: $rating")

            tvDescription.text = description
            etDescription.setText(description)
            if(rating != null) {
                ratingBar.rating = rating as Float
            }
            //Glide.with(this).load(description).into(imageView)
        }else{
            Toast.makeText(requireActivity(), "no path given", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        tvDescription.text = savedInstanceState?.getCharSequence("etVal")
        var tempRating = savedInstanceState?.getFloat("rating")
        if(tempRating != null ){
            ratingBar.rating = tempRating
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence("etVal", description)
        outState.putFloat("rating", rating!!)
    }

    override fun onPause() {
        super.onPause()
        //val intent: Intent = Intent(requireActivity(), MainActivity::class.java)
        val intent = Intent()
        intent.putExtra("path", path)
        intent.putExtra("rating", rating)
        intent.putExtra("description", description)

        println("in Intent fragment")
        println("path $path ")
        println("rating $rating ")
        println("description $description ")
        println("Activity.RESULT_OK ${Activity.RESULT_OK} ")
        println("activity ${activity} ")

        activity?.setResult(Activity.RESULT_OK, intent)
    }
}