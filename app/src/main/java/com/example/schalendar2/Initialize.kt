package com.example.schalendar2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.security.AccessController.getContext
import java.util.Date
import android.content.pm.ResolveInfo
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt


class Initialize : AppCompatActivity() {
    //Camera doesn't work!!! IDK Why
    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    private lateinit var imageView: ImageView
    private val CAMERA_PERMISSION_CODE = 101
    private val REQUEST_IMAGE_CAPTURE = 1


    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto: Boolean ->
        if (didTakePhoto && photoName != null) {
            Toast.makeText(this, "Photo taken", Toast.LENGTH_SHORT).show()
            updatePhoto(photoName)
        }
    }

    private var photoName: String? = null

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try{
            imageView.setImageURI(galleryUri)
        }catch(e:Exception){
            e.printStackTrace()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initialize)
        val spinner: Spinner = findViewById(R.id.menuspinnerinitialize)
        val options = resources.getStringArray(R.array.menu_array)

        ArrayAdapter.createFromResource(
            this,
            R.array.menu_array,
            R.layout.spinner_list
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_list)
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
                    "View Calendar" -> startSpecificActivity(ViewCalendar::class.java)
                    "Paramètres" -> startSpecificActivity(Settings::class.java)
                    "Liste des Tâches" -> startSpecificActivity(ToDo::class.java)
                    "Centre D'aide" -> startSpecificActivity(HelpCenter::class.java)
                    "Voir Le Calendrier" -> startSpecificActivity(ViewCalendar::class.java)
                    "设置" -> startSpecificActivity(Settings::class.java)
                    "去做" -> startSpecificActivity(ToDo::class.java)
                    "帮助中心" -> startSpecificActivity(HelpCenter::class.java)
                    "查看日历" -> startSpecificActivity(ViewCalendar::class.java)
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

        findViewById<Button>(R.id.choosephotobutton).setOnClickListener {
            galleryLauncher.launch("image/*")
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

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = this.packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    private fun openCamera() {
        /*val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            launcher.launch(takePictureIntent)
        }*/

        photoName = "IMG_${Date()}.JPG"
        val photoFile = File(this@Initialize.applicationContext.filesDir, photoName)
        val photoUri = FileProvider.getUriForFile(
            this@Initialize,
            "com.bignerdranch.android.schalendar2.fileprovider",
            photoFile
        )


        takePhoto.launch(photoUri)

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

    fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        // Read in the dimensions of the image on disk
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        // Figure out how much to scale down by
        val sampleSize = if (srcHeight <= destHeight && srcWidth <= destWidth) {
            1
        } else {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth

            minOf(heightScale, widthScale).roundToInt()
        }

        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
            inSampleSize = sampleSize
        })
    }

    private fun updatePhoto(photoFileName: String?) {
        if (imageView.tag != photoFileName) {
            val photoFile = photoFileName?.let {
                File(this@Initialize.applicationContext.filesDir, it)
            }

            if (photoFile?.exists() == true) {
                imageView.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )
                    imageView.setImageBitmap(scaledBitmap)
                    imageView.tag = photoFileName
                }
            } else {
                Toast.makeText(this, "Photo does not exist", Toast.LENGTH_SHORT).show()
                imageView.setImageBitmap(null)
                imageView.tag = null
            }
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
