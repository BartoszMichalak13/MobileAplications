package com.example.mygallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GalleryAdapter(context: Context, images: List<String>, photoListener: PhotoListener) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var images: List<String>
    protected lateinit var photoListener: PhotoListener

    init{
        this.context = context
        this.images = images
        this.photoListener = photoListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.gallery_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var image: String = images[position]

        Glide.with(context).load(image).into(holder.image)
        holder.itemView.setOnClickListener{
            photoListener.onPhotoClick(image)
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        init {
            image = itemView.findViewById(R.id.image)
        }

    }
    
    interface PhotoListener{
        fun onPhotoClick(path: String)
    }



}