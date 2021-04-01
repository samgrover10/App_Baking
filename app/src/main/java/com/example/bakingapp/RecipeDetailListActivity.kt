package com.example.bakingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.properties.Delegates

class RecipeDetailListActivity : AppCompatActivity(){
    var id by Delegates.notNull<Long>()
    var mTwoPane by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail_list)
        val intent = intent
         id = intent.getLongExtra("recipe_id",-1)
        Log.i("RecipeDetailList",id.toString())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fragmentManager = supportFragmentManager
//        if(savedInstanceState == null ){
//            val fragment = RecipeDetailListFragment.newInstance(id)
//            fragmentManager.beginTransaction().add(R.id.frag_list_container,fragment).commit()
//        }
        if(findViewById<FrameLayout>(R.id.recipe_detail_list)!=null){
            mTwoPane=true
            val fragment = RecipeDetailListFragment.newInstance(id,mTwoPane)
            fragmentManager.beginTransaction().add(R.id.recipe_detail_list,fragment).commit()
            val stepDetailFragment = StepDetailFragment.newInstance(id,false,-2)
            fragmentManager.beginTransaction().add(R.id.detail_info,stepDetailFragment).commit()
        }else{
            mTwoPane=false
            val fragment = RecipeDetailListFragment.newInstance(id,mTwoPane)
            fragmentManager.beginTransaction().add(R.id.frag_list_container,fragment).commit()
        }
    }
}