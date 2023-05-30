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
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.databinding.ActivityMainBinding
import com.example.mygallery.databinding.FragmentMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewPager = binding.viewpagerID
        val tabLayout = binding.tablayoutID

        var vpAdapter: VPAdapter = VPAdapter(this)

        viewPager.adapter = vpAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) tab.text = "Photos"
            if (position == 1) tab.text = "Camera"

        }.attach()

    }

}