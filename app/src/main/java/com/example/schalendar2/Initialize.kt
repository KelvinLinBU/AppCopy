package com.example.schalendar2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
class Initialize : AppCompatActivity() {
    //Camera doesn't work!!! IDK Why
    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    private lateinit var imageView: ImageView
    private val CAMERA_PERMISSION_CODE = 101
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initialize)
        val spinner: Spinner = findViewById(R.id.menuspinnerinitialize)
        val options = resources.getStringArray(R.array.menu_array)

        ArrayAdapter.createFromResource(
            this,
            R.array.menu_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item from the spinner
                val selectedItem: String = options[position]
                // Start the corresponding activity based on the selected item
                when (selectedItem) {
                    "Settings" -> startSpecificActivity(Settings::class.java)
                    "To-Do" -> startSpecificActivity(ToDo::class.java)
                    "Help Center" -> startSpecificActivity(HelpCenter::class.java)
                    // Add more cases for other options if needed
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (if necessary)
            }

        }
        imageView = findViewById(R.id.calendarimageview)

        findViewById<Button>(R.id.takephotobutton).setOnClickListener {
            requestCameraPermission()
        }
    }

    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.apply {
                visibility = View.VISIBLE
                setImageBitmap(imageBitmap)
            }
        } else {
            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            launcher.launch(takePictureIntent)
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            // Permission already granted
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
