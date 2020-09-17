package eu.amtoft.breadwowtools

import android.app.ActionBar
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.PopupWindow.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.api.CharacterMedia
import eu.amtoft.breadwowtools.api.MountContainer
import kotlinx.android.synthetic.main.fragment_character.view.*
import kotlinx.android.synthetic.main.popup_add_char.view.*

class CharacterFragment : Fragment() {

    private lateinit var adapter: CharacterAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_character, container, false)

        val characterRecycler = rootView.findViewById(R.id.characterRecycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(context)

        characterRecycler.layoutManager = linearLayoutManager

        adapter = CharacterAdapter(CharacterCollection.characters)
        characterRecycler.adapter = adapter

        val queue = Volley.newRequestQueue(context)

        CharacterCollection.characters.forEach() {
            // Instantiate the RequestQueue.
            var url = "https://" +
                    it.region +
                    ".api.blizzard.com/profile/wow/character/" +
                    it.realm.toLowerCase().replace("-", "").replace(" ", "-").replace("'", "") +
                    "/" +
                    it.name.toLowerCase() +
                    "/character-media?namespace=profile-" +
                    it.region +
                    "&locale=en_GB&access_token=USm37o1MXYHe3u44vwvdOEDZE2zHkuWzHf"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    it.imageUrl = convertJson(response)
                    adapter.notifyDataSetChanged()
                    CharacterCollection.saveCharacterList((activity as MainActivity?)!!)

                },
                {
                    Log.v("JSON", "That didn't work!")
                }
            )
            // Add the request to the RequestQueue.
            queue.add(stringRequest)

        }





        rootView.button.setOnClickListener {
            Log.v("MAIN", "In on click listener")
            // Initialize a new layout inflater instance
            val inflater: LayoutInflater =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_add_char, null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.LEFT
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.LEFT
                popupWindow.exitTransition = slideOut

            }

            TransitionManager.beginDelayedTransition(rootView.root_layout)
            popupWindow.showAtLocation(
                rootView.root_layout, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
            popupWindow.softInputMode = SOFT_INPUT_ADJUST_PAN
            popupWindow.isFocusable = true
            popupWindow.isOutsideTouchable = false
            popupWindow.update()
            popupWindow.contentView.name.requestFocus()


        }

        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    fun convertJson(json: String): String {
        var gson = Gson()
        var characterMedia = gson.fromJson<CharacterMedia>(json, CharacterMedia::class.java)
        return characterMedia.avatar_url
    }

    fun getImage(url: String) {

    }
}