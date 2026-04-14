package com.tushar.universityeventapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tushar.universityeventapp.databinding.ActivityEventListBinding

class EventListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventList = listOf(
            Event("Tech Fest", "Hall A", "100 seats"),
            Event("Sports Day", "Field", "200 seats")
        )

        val adapter = EventAdapter(eventList) {
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("title", it.title)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
