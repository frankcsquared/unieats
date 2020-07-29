package com.example.unieats.ui.search

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.unieats.R
import com.example.unieats.models.Food

class MyListAdapter(private val context: Activity, private val title: Array<String?>, private val food: Array<Food>)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]
        if(food[position].image != null) {
            imageView.setImageBitmap(toImage(food[position].image!!))
        }

        subtitleText.text = food[position].calories.toString()

        return rowView
    }

    fun toImage(inString: String?) : Bitmap?{
        val encodedImage: String;
        return if(inString != null) {
            encodedImage = inString!!.replace("data:image/jpeg;base64,","")
            val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            decodedByte
        }else{
            null
        }
    }
}


