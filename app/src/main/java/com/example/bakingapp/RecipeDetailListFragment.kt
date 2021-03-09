package com.example.bakingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ID = "id"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetailListFragment : Fragment(),RecipeDetailListAdapter.OnStepOrIngredientClickListener {
    // TODO: Rename and change types of parameters
    private var id: Long? = null

     lateinit var mAdapter :RecipeDetailListAdapter
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong(ARG_ID)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        arguments?.getLong(ARG_ID)?.let { id = it  }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.detail_list_rv)
        val linearLayoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
//        val recipeId = arguments?.getLong("id")
        Log.i("Fragment",id.toString())
        val stepDescriptions :List<String> = JsonUtil.getStepDescriptionsFromId(mContext,id)
        mAdapter = RecipeDetailListAdapter(stepDescriptions,this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeDetailListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Long) =
            RecipeDetailListFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, param1)
                }
            }
    }

    override fun onStepOrIngredientClick(position: Int) {
        val intentToStepDetail = Intent(mContext,StepDetailActivity::class.java)
        if(position == -2){
            intentToStepDetail.putExtra("recipe_id",id)
        }else{
            intentToStepDetail.putExtra("id_food",id)
            intentToStepDetail.putExtra("step_id",position.toLong())
        }
        startActivity(intentToStepDetail)
    }
}