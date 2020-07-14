package com.example.unieats.ui.search

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.unieats.R

class MyListAdapter(private val context: Activity, private val title: Array<String?>, private val description: Array<Int?>, private val imgMap: Array<Bitmap?>/*, private val imgid: Array<Int>*/)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]
        imageView.setImageBitmap(imgMap[position])

        subtitleText.text = description[position].toString()

        return rowView
    }
}