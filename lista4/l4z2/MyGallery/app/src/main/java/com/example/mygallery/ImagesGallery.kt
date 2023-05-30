package com.example.mygallery

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class ImagesGallery {
    companion object {
        fun listOfImages(context: Context): ArrayList<String> {
            val uri: Uri
            val cursor: Cursor
            var column_index_folder_name: Int
            val column_index_data: Int

            val listOfAllImages: ArrayList<String> = ArrayList()
            var absolutePathOfImage: String
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection: Array<String> = arrayOf<String>(
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            //String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

            val orderBy = MediaStore.Video.Media.DATE_TAKEN
            cursor = context.contentResolver.query(uri, projection, null, null, orderBy + " DESC")!!
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)


            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                println(absolutePathOfImage)
                listOfAllImages.add(absolutePathOfImage)
            }
            return listOfAllImages

        }
    }
}