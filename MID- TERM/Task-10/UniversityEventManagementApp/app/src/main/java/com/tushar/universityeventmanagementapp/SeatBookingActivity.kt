package com.tushar.universityeventapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tushar.universityeventapp.databinding.ActivitySeatBookingBinding

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeatBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 1..48) {
            val btn = Button(this)
            btn.text = i.toString()

            btn.setOnClickListener {
                btn.isSelected = !btn.isSelected
                btn.setBackgroundColor(
                    if (btn.isSelected) 0xFF00FF00.toInt()
                    else 0xFFFFFFFF.toInt()
                )
            }

            binding.gridLayout.addView(btn)
        }
    }
}
