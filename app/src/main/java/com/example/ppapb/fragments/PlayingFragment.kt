package com.example.ppapb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppapb.adapter.UserAdapter
import com.example.ppapb.database.Note
import com.example.ppapb.database.NoteDao
import com.example.ppapb.database.NoteRoomDatabase
import com.example.ppapb.databinding.FragmentPlayingBinding
import com.example.ppapb.item.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayingFragment : Fragment() {
    private lateinit var binding : FragmentPlayingBinding
    private lateinit var database : DatabaseReference
    private lateinit var recyclerViewItem : RecyclerView
    private lateinit var itemAdapter : UserAdapter
    private lateinit var itemList : ArrayList<Movie>
    private lateinit var searchView : androidx.appcompat.widget.SearchView

    private lateinit var dao: NoteDao



    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayingBinding.inflate(layoutInflater)

        recyclerViewItem = binding.userRecyclerView
        recyclerViewItem.setHasFixedSize(true)
        recyclerViewItem.layoutManager = LinearLayoutManager(requireActivity())

        itemAdapter = UserAdapter(emptyList())
        recyclerViewItem.adapter = itemAdapter


        // Initialize Room database
        dao = NoteRoomDatabase.getDatabase(requireContext()).dao()

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().getReference("Admin")

        // Fetch data from Firebase and update itemList
        fetchFilmFromFirebase()

        return binding.root
    }

    private fun fetchFilmFromFirebase() {

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filmList = mutableListOf<Note>()

                for (dataSnapshot in snapshot.children) {
                    val filmEntity = dataSnapshot.getValue(Note::class.java)
                    filmEntity?.let { filmList.add(it) }
                }

                // Update Room database with the new data from Firebase
                GlobalScope.launch(Dispatchers.IO) {
                    dao.deleteAllFilm()
                    dao.insertFilm(filmList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error if needed
            }
        })

        // Observe changes in the LiveData from Room and update the adapter
        dao.getAllFilm().observe(viewLifecycleOwner, Observer { films ->
            itemAdapter.updateData(films)
        })
    }









    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}