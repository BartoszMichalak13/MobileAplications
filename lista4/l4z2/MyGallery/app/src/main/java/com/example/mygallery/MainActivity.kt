package com.example.mygallery

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding

    lateinit var recyclerView: RecyclerView
    lateinit var galleryAdapter: GalleryAdapter
    lateinit var images: List<String>
    lateinit var gallery_number: TextView

    var ratingMap: MutableMap<String, Float> = mutableMapOf()
    var descriptionMap: MutableMap<String, String> = mutableMapOf()


     var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        println("Czy tu wchodzi?")
        println("result.resultCode ${result.resultCode}")
        println("Activity.RESULT_OK ${Activity.RESULT_OK}")
        //if (result.resultCode == Activity.RESULT_OK) {

        val intent = result.data
        // Handle the Intent
        //do stuff here
        var extr = intent?.extras
        println("startForResutl, res code = OK")
        println("extr $extr")

        if(extr != null){
            var newPath: String = extr.getString("path")!!
            var newRating = extr.getFloat("rating")
            var newDescription = extr.getString("description")
            println("newPath $newPath ")
            println("newRating $newRating ")
            println("newDescription $newDescription ")

            ratingMap[newPath] = newRating
            descriptionMap[newPath] = newDescription!!
        }

        //}
    }

    val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        gallery_number = binding.galleryNumber//findViewById(R.id.gallery_number)//binding.g
        recyclerView = binding.recyclerviewGalleryImages


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already granted this permission!",
                Toast.LENGTH_SHORT).show();
            loadImages()
        }else{

            requestStoragePermission()
        }

        binding.tkPhotoBtn.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed because of this and that")
                .setPositiveButton("ok",
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this@MainActivity, arrayOf<String>(
                                Manifest.permission.READ_MEDIA_IMAGES
                            ), STORAGE_PERMISSION_CODE
                        )
                    })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .create().show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.READ_MEDIA_IMAGES),
                STORAGE_PERMISSION_CODE
            )
        }
    }
    private fun mapsInit(images: List<String>){

        images.forEach {
            ratingMap[it] = 0f
            descriptionMap[it] = ""

        }
        println("ratingMap $ratingMap")
        println("descriptionMap $descriptionMap")
    }

    private fun loadImages(){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this,4)
        images = ImagesGallery.listOfImages(this)

        if(ratingMap.isEmpty() && descriptionMap.isEmpty()) {
            println("INIT ")
            mapsInit(images)
        }

        galleryAdapter = GalleryAdapter(this, images, object: GalleryAdapter.PhotoListener{


            override fun onPhotoClick(path: String) {
                Toast.makeText(this@MainActivity, ""+path, Toast.LENGTH_SHORT).show()
                val intent2 = Intent(this@MainActivity, PhotoActivity::class.java)
                intent2.putExtra("address", path)
                intent2.putExtra("rating", ratingMap[path])
                intent2.putExtra("description", descriptionMap[path])
                //startActivity(intent2)

                println("HELLO on Photo Click")
                startForResult.launch(intent2)

            }
        })

        recyclerView.adapter = galleryAdapter

        gallery_number.text = "Photos (" + images.size + ")"
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            //if(allPermissionGranted()){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Read ext storage permission granted", Toast.LENGTH_LONG).show()
                loadImages()
            }else{
                Toast.makeText(this, "Read ext storage permission denied", Toast.LENGTH_LONG).show()
                finish()

            }
        }
    }

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED

//        Constants.READ_PERM.all{
//            ContextCompat.checkSelfPermission(
//                baseContext, it
//            ) == PackageManager.PERMISSION_GRANTED
//        }
}