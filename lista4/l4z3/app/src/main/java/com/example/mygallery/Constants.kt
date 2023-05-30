package com.example.mygallery

import android.Manifest

object Constants {

    const val TAG = "cameraX"
    const val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSIONS = 123
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    val READ_PERM = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

}
