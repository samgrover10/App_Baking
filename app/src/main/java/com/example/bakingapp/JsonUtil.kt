package com.example.bakingapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class JsonUtil {

    companion object {
    private fun getJsonFromAsset(context : Context , fileName : String):String? {
        val jsonString :String
        try{
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        }catch (ioException : IOException){
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getRecipeFromJson(context: Context):List<Food>? {
        val foodList = mutableListOf<Food>()
        val json = getJsonFromAsset(context, "baking.json")

        if (json != null) {
            val jsonArray = JSONArray(json)
            for (i in 0..(jsonArray.length() - 1)) {
                val jsonObj = jsonArray.getJSONObject(i)
                val name = jsonObj.getString("name")
                val id = jsonObj.getLong("id")
                val photoUrl = jsonObj.getString("image")
                foodList.add(Food(id,name,photoUrl))
            }
            return foodList
        }else
            return null
    }
        fun getStepDescriptionsFromId(context: Context, recipeId : Long?):List<String> {
            val stepDescriptions= mutableListOf<String>()
            val json = getJsonFromAsset(context,"baking.json")

            val jsonArray = JSONArray(json)
            val jsonObject = jsonArray.getJSONObject((recipeId?.toInt() ?: 1) -1)
            val stepsArray = jsonObject.getJSONArray("steps")
            for(i in 0..(stepsArray.length()-1)) {
                val stepObject = stepsArray.getJSONObject(i)
                val shortDescription = stepObject.getString("shortDescription")
                stepDescriptions.add(shortDescription)
                Log.i("sam",shortDescription +" "+ i.toString())
            }
                return stepDescriptions
        }

        fun getIngredientsFromId(context: Context,recipeId:Long?):List<Ingredients>{
            val ingredients= mutableListOf<Ingredients>()
            val json = getJsonFromAsset(context,"baking.json")

            val jsonArray = JSONArray(json)
            val jsonObject = jsonArray.getJSONObject((recipeId?.toInt() ?: 1) -1)
            val ingredientsArray = jsonObject.getJSONArray("ingredients")
            for(i in 0..(ingredientsArray.length()-1)){
                val ingredientObj = ingredientsArray.getJSONObject(i)
                val ingredient = ingredientObj.getString("ingredient")
                val quantity = ingredientObj.getDouble("quantity")
                val measure = ingredientObj.getString("measure")
                ingredients.add(Ingredients(ingredient,quantity,measure))
            }
            return ingredients
        }

        fun getStepDetailsFromRecipeAndStepId(context : Context,recipeId: Long?,stepId :Long? ) :StepVideoDetails{
            Log.i("E : ",stepId.toString())
            var stepVideoDetails : StepVideoDetails
            val json = getJsonFromAsset(context,"baking.json")

            val jsonArray = JSONArray(json)
            val jsonObject = jsonArray.getJSONObject((recipeId?.toInt() ?: 1) -1)
            val stepsArray = jsonObject.getJSONArray("steps")
            val stepCount = stepsArray.length().toLong()
            val stepObj = stepsArray.getJSONObject(stepId?.toInt() ?: 1)
            val videoUrl = stepObj.getString("videoURL")
            val desc = stepObj.getString("description")
            stepVideoDetails = StepVideoDetails(desc,videoUrl,stepCount)
            return stepVideoDetails
        }



}


}