package com.example.bakingapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.levitnudi.legacytableview.LegacyTableView
import kotlin.properties.Delegates


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ID = "id"
private const val ARG_IS_STEP="isStep"
private const val ARG_FOOD_ID = "food_id"

/**
 * A simple [Fragment] subclass.
 * Use the [StepDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StepDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var recipeOrStepId: Long? = null
    private var isStep:Boolean = false
    private var food_id : Long? = null
    lateinit var mContext : Context
    lateinit var player :SimpleExoPlayer
    lateinit var nextButton : Button
    lateinit var previousButton:Button
    lateinit var descTextView :TextView
    var noOfSteps:Long = 0

    override fun onAttach(activity: Activity) {
         player = SimpleExoPlayer.Builder(context!!).build()
        super.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeOrStepId = it.getLong(ARG_ID)
            isStep = it.getBoolean(ARG_IS_STEP)
            food_id = it.getLong(ARG_FOOD_ID)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_step_detail, container, false)
        val legacyTableView: LegacyTableView =
            rootView.findViewById<LegacyTableView>(R.id.ingredients_table)
        val playerView : StyledPlayerView = rootView.findViewById(R.id.video_player)
        descTextView = rootView.findViewById(R.id.step_detail_tv)
        nextButton = rootView.findViewById(R.id.next_button)
        previousButton  = rootView.findViewById(R.id.previous_button)


        if(isStep){
            showPrevNext(recipeOrStepId)
            val unavailableTv = rootView.findViewById<TextView>(R.id.unavailable)
            nextButton.setOnClickListener{
                recipeOrStepId = recipeOrStepId?.plus(1);
                showPrevNext(recipeOrStepId)
                val stepVideoDetails = JsonUtil.getStepDetailsFromRecipeAndStepId(mContext,food_id,(recipeOrStepId))
                val videoUrl = stepVideoDetails.videoUrl
                if(videoUrl.isEmpty())
                    unavailableTv.visibility=View.VISIBLE
                else
                    unavailableTv.visibility=View.GONE
                val desc = stepVideoDetails.description
                Log.i("tag",desc+" and "+videoUrl.toString())
                descTextView.text = desc
                val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
                player.setMediaItem(mediaItem)
                player.prepare();
                player.playWhenReady=true;
            }
            previousButton.setOnClickListener{
                recipeOrStepId = recipeOrStepId?.minus(1);
                showPrevNext(recipeOrStepId)
                val stepVideoDetails = JsonUtil.getStepDetailsFromRecipeAndStepId(mContext,food_id,(recipeOrStepId))
                val videoUrl = stepVideoDetails.videoUrl
                if(videoUrl.isEmpty())
                    unavailableTv.visibility=View.VISIBLE
                else
                    unavailableTv.visibility=View.GONE
                val desc = stepVideoDetails.description
                Log.i("tag",desc+" and "+videoUrl.toString())
                descTextView.text = desc
                val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
                player.setMediaItem(mediaItem)
                player.prepare();
                player.playWhenReady=true;
            }
            val stepVideoDetails = JsonUtil.getStepDetailsFromRecipeAndStepId(mContext,food_id,recipeOrStepId)
            noOfSteps = stepVideoDetails.stepCount
            val videoUri = stepVideoDetails.videoUrl
            if(videoUri.isEmpty())
                unavailableTv.visibility=View.VISIBLE
            else
                unavailableTv.visibility=View.GONE
            val desc = stepVideoDetails.description
            legacyTableView.visibility = View.GONE
            playerView.visibility = View.VISIBLE
            descTextView.visibility = View.VISIBLE
            descTextView.text = desc
            playerView.player = player
            val mediaItem: MediaItem = MediaItem.fromUri(videoUri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
        }else {
            //Ingredients to show
            playerView.visibility= View.GONE
            legacyTableView.visibility = View.VISIBLE
            descTextView.visibility = View.GONE
            previousButton.visibility=View.GONE
            nextButton.visibility=View.GONE
            LegacyTableView.insertLegacyTitle("Id", "Ingredient", "Quantity", "Unit")
            val ingredients = JsonUtil.getIngredientsFromId(mContext, recipeOrStepId)
            for (i in 0..((ingredients.size) - 1)) {
                LegacyTableView.insertLegacyContent(
                    (i + 1).toString(),
                    ingredients.get(i).ingredient,
                    ingredients.get(i).quantity.toString(),
                    ingredients.get(i).measure
                )
            }
            legacyTableView.setTitle(LegacyTableView.readLegacyTitle())
            legacyTableView.setContent(LegacyTableView.readLegacyContent())
            legacyTableView.setTablePadding(40)
            legacyTableView.setZoomEnabled(true)
            legacyTableView.setShowZoomControls(true)
            legacyTableView.setInitialScale(100)
            legacyTableView.setTitleTextSize(50)
            legacyTableView.setTitleFont(LegacyTableView.BOLD)
            legacyTableView.setContentTextSize(40)
            legacyTableView.setTheme(LegacyTableView.MAASAI)
            legacyTableView.build()
        }
        return rootView
    }

    fun previousClicked(view : View){

    }

//
//     fun nextClicked(view:View) {
//        val stepVideoDetails = JsonUtil.getStepDetailsFromRecipeAndStepId(mContext,food_id,(recipeOrStepId!! + 1))
//        val videoUrl = stepVideoDetails.videoUrl
//        val desc = stepVideoDetails.description
//        Log.i("tag",desc+" and "+videoUrl.toString())
//        descTextView.text = desc
//        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
//        player.setMediaItem(mediaItem)
//        player.prepare();
//        player.playWhenReady=true;
//    }
    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
    }
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    fun showPrevNext(stepId:Long?){
        if(stepId == 0L ){
            previousButton.visibility = View.GONE
            nextButton.visibility = View.VISIBLE
        }else if(stepId == noOfSteps-1){
            previousButton.visibility = View.VISIBLE
            nextButton.visibility = View.GONE
        } else {
            previousButton.visibility = View.VISIBLE
            nextButton.visibility = View.VISIBLE
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StepDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(recipeOrStepId: Long, isStep: Boolean, food_id: Long) =
            StepDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, recipeOrStepId)
                    putBoolean(ARG_IS_STEP, isStep)
                    putLong(ARG_FOOD_ID, food_id)
                }
            }
    }
}