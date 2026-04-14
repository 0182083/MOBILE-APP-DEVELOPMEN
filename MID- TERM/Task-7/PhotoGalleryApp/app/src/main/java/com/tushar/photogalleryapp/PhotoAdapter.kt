package com.tushar.photogalleryapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*

class PhotoAdapter(
    private val context: Context,
    private var photos: MutableList<Photo>
) : BaseAdapter() {

    var selectionMode = false

    override fun getCount() = photos.size
    override fun getItem(position: Int) = photos[position]
    override fun getItemId(position: Int) = photos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(context, R.layout.item_photo, null)
        val image = view.findViewById<ImageView>(R.id.imageView)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        val photo = photos[position]
        image.setImageResource(photo.imageRes)

        checkBox.visibility = if (selectionMode) View.VISIBLE else View.GONE
        checkBox.isChecked = photo.isSelected

        return view
    }

    fun updateList(newList: MutableList<Photo>) {
        photos = newList
        notifyDataSetChanged()
    }
}
