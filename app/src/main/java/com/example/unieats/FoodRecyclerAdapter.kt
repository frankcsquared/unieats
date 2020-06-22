package com.example.unieats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.unieats.models.Food
import kotlinx.android.synthetic.main.layout_food_list_item.view.*

class FoodRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Food> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoodViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.layout_food_list_item, parent, false)
            //this will be the same in any recyclerview
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FoodViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(food: List<Food>) {
        items = food
    }

    class FoodViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val foodName = itemView.food_name
        val foodImage = itemView.food_image
        val foodCals = itemView.food_calories

        fun bind(Food: Food) {
            foodName.text = Food.name
            foodCals.text = Food.calories.toString()

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(Food.image)
                .into(foodImage)
        }
    }
}