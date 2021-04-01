package com.example.bakingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.properties.Delegates

class StepDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_detail)
        val intent = intent
        val fragmentManager = supportFragmentManager
        var food_id : Long = -2
        var isStep :Boolean = false
        var recipeOrStepid by Delegates.notNull<Long>()
        if(intent.hasExtra("recipe_id")){
            isStep = false
             recipeOrStepid = intent.getLongExtra("recipe_id",-1)
        }else if(intent.hasExtra("step_id")){
            isStep = true
            food_id = intent.getLongExtra("id_food",-1)
            recipeOrStepid = intent.getLongExtra("step_id",-1)
            Log.i("stepDetail",recipeOrStepid.toString())
        }
        val stepDetailFragment = StepDetailFragment.newInstance(recipeOrStepid,isStep,food_id)
        if(savedInstanceState == null)
        fragmentManager.beginTransaction().add(R.id.frag_detail_container,stepDetailFragment).commit()
    }
}