package com.example.bakingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeListActivity : AppCompatActivity(),RecipeListAdapter.OnRecipeClickListener {
   lateinit var mAdapter: RecipeListAdapter
   lateinit var mRecyclerView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        mAdapter = RecipeListAdapter(this,this)
        if(findViewById<RecyclerView>(R.id.recipe_grid)!=null){
            mRecyclerView=findViewById(R.id.recipe_grid)
            val gridLayoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = gridLayoutManager
        }else {
            mRecyclerView = findViewById(R.id.recipe_rv)
            val linearLayoutManagaer =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = linearLayoutManagaer
        }




        val foodList = JsonUtil.getRecipeFromJson(this)
        initializeList(foodList)

    }
    fun initializeList(listOfFood : List<Food>?){
        mAdapter.food=listOfFood
        mAdapter.notifyDataSetChanged()
    }

    override fun onRecipeClick(id: Long) {
        val intent = Intent(this@RecipeListActivity,RecipeDetailListActivity::class.java)
        Log.i("clicked",id.toString())
        if(id.equals(-1)) {
            Log.i("RecipeListActivity","No ID")
        }else{
            intent.putExtra("recipe_id", id)
            startActivity(intent)
        }
    }
}