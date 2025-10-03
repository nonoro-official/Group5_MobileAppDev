package com.example.myapplication


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter (
    val context: Context,
    val pet: Array<String>,
    val image: IntArray
) : BaseAdapter() {

    override fun getCount(): Int {
        return pet.size
    }

    override fun getItem(position: Int): Any? {
        return pet[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.gallery_item, parent, false)

        val imageView = itemView.findViewById<ImageView>(R.id.gallery_image)
        val textView = itemView.findViewById<TextView>(R.id.image_name)

        imageView.setImageResource(image[position])
        textView.text = pet[position]

        return itemView
    }
}