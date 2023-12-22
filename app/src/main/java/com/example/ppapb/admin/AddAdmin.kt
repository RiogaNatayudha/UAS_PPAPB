package com.example.ppapb.admin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.ppapb.R
import com.example.ppapb.databinding.AdminAddBinding
import com.example.ppapb.item.Movie
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class AddAdmin : AppCompatActivity() {



    private lateinit var binding: AdminAddBinding
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri
                binding.imageViewAdd.setImageURI(uri)
                // Optionally, you can call uploadData(imageUri) here if needed
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = AdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.adminListAddButton.setOnClickListener {
            Log.d("KEPENCET","YAAA")
            Log.d("KEPENCET","YAAA")
            Log.d("KEPENCET","YAAA")
            Log.d("KEPENCET","YAAA")

            uploadData(imageUri)
        }

        binding.adminListAddImageUrl.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.backadd.setOnClickListener{
            startActivity(Intent(this@AddAdmin, MainAdmin::class.java))
        }
    }

    private fun uploadData(imageUri: Uri? = null) {
        if (imageUri != null) {
            val title: String = binding.adminListAddJudul.text.toString()
            val author: String = binding.adminListAddPembuatDanRating.text.toString()
            val genre: String = binding.adminListAddGenre.text.toString()
            val storyline: String = binding.adminListAddStoryline.text.toString()
            val description: String = binding.adminListAddDescription.text.toString()

            val imageId = UUID.randomUUID().toString()

            Log.d("msgggBELOMMM","${title} , ${imageId}")
            Log.d("msgggBELOMMM","${title} , ${imageId}")


            if (title.isNotEmpty() && author.isNotEmpty() && genre.isNotEmpty() && storyline.isNotEmpty() && description.isNotEmpty() && imageUri != null) {

                storageReference = FirebaseStorage.getInstance().reference.child("images/$imageId")
                val uploadTask: UploadTask = storageReference.putFile(imageUri)

                uploadTask.addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { imageUrl ->
                        val item = Movie(title, imageUrl.toString(),author, description,genre, storyline )

                        Log.d("ITEM COBA","${item.toString()}")
                        Log.d("ITEM COBA","${item.toString()}")
                        Log.d("ITEM COBA","${item.toString()}")
                        Log.d("ITEM COBA","${item.toString()}")

                        database = FirebaseDatabase.getInstance().getReference("Admin")
                        database.child(imageId).setValue(item).addOnCompleteListener {

                                Log.d("msgUDAHHH","${item.toString()} , ${imageId}")
                                Log.d("msgUDAHHH","${item.toString()} , ${imageId}")
                                Log.d("msgUDAHHH","${item.toString()} , ${imageId}")


                                startActivity(Intent(this@AddAdmin, MainAdmin::class.java))
                                Toast.makeText(
                                    this@AddAdmin,
                                    "Data Uploaded Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this@AddAdmin,
                                    "Adding Data Failed!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@AddAdmin, "Image Upload Failed!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this@AddAdmin,
                    "Please fill in all fields and select an image",
                    Toast.LENGTH_SHORT
                ).show()
                // Hentikan proses upload dan kembali ke halaman AddAdmin tanpa melanjutkan ke MainAdmin
                return
            }
        }
    }

    private fun showNotification(message: String) {
        val channelId = "MyChannelId"
        val notificationId = 1

        createNotificationChannel()

        val builder = NotificationCompat.Builder(this@AddAdmin, channelId)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("PPAPB UAS Notification")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

//        with(NotificationManagerCompat.from(this)) {
//            notify(notificationId, builder.build())
//        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannel"
            val descriptionText = "My Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("MyChannelId", name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}