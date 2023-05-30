package com.example.mygallery

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class PhotoFragment : Fragment() {


    lateinit var imageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_photo, container, false)
        imageView = view.findViewById(R.id.selectedPhoto)
        val extras = requireActivity().intent.extras
        var path: String? = null
        if (extras != null) {
            path = extras.getString("address")
            println("path: $path")
            Glide.with(this).load(path).into(imageView)
        }else{
            Toast.makeText(requireActivity(), "no path given", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}