package com.example.bakingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeListAdapter(mContext: Context,mRecipeClickListener: OnRecipeClickListener) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    var food :  List<Food>? = null
    val context : Context = mContext
    private val recipeClickListener : OnRecipeClickListener = mRecipeClickListener

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
     interface OnRecipeClickListener {
        fun onRecipeClick(id:Long)
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recipe_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.foodNameTv.text = food?.get(position)?.name
        val photoUrl  = food?.get(position)?.photoUrl
        Glide.with(context).load(photoUrl).into(viewHolder.foodIv)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = food?.size ?: 0


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val foodNameTv: TextView
        val foodIv : ImageView

        init {
            // Define click listener for the ViewHolder's View.
            foodNameTv = view.findViewById(R.id.food_name)
            foodIv = view.findViewById(R.id.food_image)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val clickedPosition = adapterPosition
            recipeClickListener.onRecipeClick(food?.get(clickedPosition)?.id ?: -1 )
        }
    }


}
