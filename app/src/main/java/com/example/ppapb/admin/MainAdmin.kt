package com.example.ppapb.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppapb.activity.SplashScreenActivity
import com.example.ppapb.databinding.AdminMainBinding
import com.example.ppapb.item.Movie
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class MainAdmin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : AdminMainBinding
    private lateinit var database : DatabaseReference
    private lateinit var itemAdapter : AdminAdapter
    private lateinit var itemList : ArrayList<Movie>
    private lateinit var recyclerViewItem : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewItem = binding.adminRecyclerView
        recyclerViewItem.setHasFixedSize(true)
        recyclerViewItem.layoutManager = LinearLayoutManager(this)

        itemList = arrayListOf()
        itemAdapter = AdminAdapter(itemList)
        recyclerViewItem.adapter = itemAdapter

        with(binding){
            adminAddButton.setOnClickListener{
                startActivity(Intent(this@MainAdmin,AddAdmin::class.java))
            }
            adminLogout.setOnClickListener{
                auth = Firebase.auth
                auth.signOut()
                startActivity(Intent(this@MainAdmin,SplashScreenActivity::class.java))
            }
        }

        database = FirebaseDatabase.getInstance().getReference("Admin")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the existing list
                itemList.clear()

                // Iterate through the snapshot and add items to the list
                for (dataSnapshot in snapshot.children) {
                    val item = dataSnapshot.getValue(Movie::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }

                // Notify the adapter that the data has changed
                itemAdapter.notifyDataSetChanged()
                Log.d("msg",itemList.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if needed
                Toast.makeText(this@MainAdmin, "Data retrieval failed!", Toast.LENGTH_SHORT).show()
            }
        })


    }


}
