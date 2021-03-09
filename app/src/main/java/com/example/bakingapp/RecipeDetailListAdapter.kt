package com.example.bakingapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeDetailListAdapter(private val dataSet: List<String>, OnItemClickListener: OnStepOrIngredientClickListener) :
    RecyclerView.Adapter<RecipeDetailListAdapter.ViewHolder>() {

    val INGREDIENTS = 0
    val STEP_DESCRIPTION = 1
    val mOnStepOrIngredientClickListener :OnStepOrIngredientClickListener = OnItemClickListener


    public interface OnStepOrIngredientClickListener{
        fun onStepOrIngredientClick(position: Int)
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val layoutId = if(viewType == INGREDIENTS)R.layout.ingredients_item else R.layout.detail_list_item
        val view = LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if(getItemViewType(position) == STEP_DESCRIPTION){
        viewHolder.stepDescription.text = dataSet[position-1]}
        else if(getItemViewType(position) == INGREDIENTS){
            viewHolder.stepDescription.text = "INGREDIENTS"
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position == 0 )INGREDIENTS else STEP_DESCRIPTION
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val stepDescription: TextView

        init {
            // Define click listener for the ViewHolder's View.
            stepDescription = view.findViewById(R.id.step_description)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
        mOnStepOrIngredientClickListener.onStepOrIngredientClick(if(itemViewType == INGREDIENTS) -2 else (adapterPosition-1))
            Log.i("RecipeDetail",adapterPosition.toString())
        }
    }


}
