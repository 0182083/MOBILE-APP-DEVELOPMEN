package com.tushar.universityeventapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tushar.universityeventapp.databinding.ItemEventBinding

class EventAdapter(
    private val list: List<Event>,
    private val onClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = list[position]

        holder.binding.txtTitle.text = event.title
        holder.binding.txtVenue.text = event.venue
        holder.binding.txtSeats.text = event.seats

        holder.itemView.setOnClickListener {
            onClick(event)
        }
    }

    override fun getItemCount() = list.size
}
