package com.tushar.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PhotoAdapter
    private lateinit var gridView: GridView
    private lateinit var selectionBar: LinearLayout
    private lateinit var txtSelected: TextView

    private var photoList = mutableListOf<Photo>()
    private var filteredList = mutableListOf<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        selectionBar = findViewById(R.id.selectionBar)
        txtSelected = findViewById(R.id.txtSelected)

        loadPhotos()

        adapter = PhotoAdapter(this, filteredList)
        gridView.adapter = adapter

        // Click
        gridView.setOnItemClickListener { _, _, position, _ ->
            val photo = filteredList[position]

            if (adapter.selectionMode) {
                photo.isSelected = !photo.isSelected
                updateSelection()
            } else {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image", photo.imageRes)
                startActivity(intent)
            }
        }

        // Long Press
        gridView.setOnItemLongClickListener { _, _, position, _ ->
            adapter.selectionMode = true
            selectionBar.visibility = LinearLayout.VISIBLE

            filteredList[position].isSelected = true
            updateSelection()
            true
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            photoList.removeAll { it.isSelected }
            filter("All")
        }

        // Category buttons
        findViewById<Button>(R.id.btnAll).setOnClickListener { filter("All") }
        findViewById<Button>(R.id.btnNature).setOnClickListener { filter("Nature") }
        findViewById<Button>(R.id.btnCity).setOnClickListener { filter("City") }
        findViewById<Button>(R.id.btnAnimals).setOnClickListener { filter("Animals") }
        findViewById<Button>(R.id.btnFood).setOnClickListener { filter("Food") }
        findViewById<Button>(R.id.btnTravel).setOnClickListener { filter("Travel") }
    }

    private fun loadPhotos() {
        photoList = mutableListOf(
            Photo(1, R.drawable.img1, "Nature1", "Nature"),
            Photo(2, R.drawable.img2, "City1", "City"),
            Photo(3, R.drawable.img3, "Animal1", "Animals"),
            Photo(4, R.drawable.img4, "Food1", "Food"),
            Photo(5, R.drawable.img5, "Travel1", "Travel"),
            Photo(6, R.drawable.img6, "Nature2", "Nature"),
            Photo(7, R.drawable.img7, "City2", "City"),
            Photo(8, R.drawable.img8, "Animal2", "Animals"),
            Photo(9, R.drawable.img9, "Food2", "Food"),
            Photo(10, R.drawable.img10, "Travel2", "Travel"),
            Photo(11, R.drawable.img11, "Nature3", "Nature"),
            Photo(12, R.drawable.img12, "City3", "City")
        )

        filter("All")
    }


    private fun filter(category: String) {
        filteredList = if (category == "All") {
            photoList.toMutableList()
        } else {
            photoList.filter { it.category == category }.toMutableList()
        }

        adapter.updateList(filteredList)
    }

    private fun updateSelection() {
        val count = filteredList.count { it.isSelected }
        txtSelected.text = "$count selected"
        adapter.notifyDataSetChanged()
    }
}
