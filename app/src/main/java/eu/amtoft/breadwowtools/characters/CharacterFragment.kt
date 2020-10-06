package eu.amtoft.breadwowtools.characters

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.AuthKey
import eu.amtoft.breadwowtools.Faction
import eu.amtoft.breadwowtools.MainActivity
import eu.amtoft.breadwowtools.R
import eu.amtoft.breadwowtools.api.CharacterInfo
import eu.amtoft.breadwowtools.api.CharacterMedia
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
        getImages(queue)

        rootView.button.setOnClickListener {
            Log.v("MAIN", "In on click listener")
            // Initialize a new layout inflater instance
            val inflater: LayoutInflater =
                requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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

            popupWindow.contentView.name.requestFocus()

            val regionArray = resources.getStringArray(R.array.regions)
            Log.v("POPUP", "Regionarray: ${regionArray.size}")
            val regionAdapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_layout,
                R.id.text,
                regionArray
            )
            popupWindow.contentView.spinnerRegion.adapter = regionAdapter

            val euArray = resources.getStringArray(R.array.eu_realms)
            val naArray = resources.getStringArray(R.array.na_realms)

            popupWindow.contentView.spinnerRegion.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        var realmAdapter: ArrayAdapter<String>
                        if (position == 0) {
                            realmAdapter = ArrayAdapter(
                                context!!,
                                R.layout.spinner_layout,
                                R.id.text,
                                euArray
                            )
                        } else {
                            realmAdapter = ArrayAdapter(
                                context!!,
                                R.layout.spinner_layout,
                                R.id.text,
                                naArray
                            )
                        }
                        popupWindow.contentView.spinnerRealm.adapter = realmAdapter
                    }
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


            popupWindow.contentView.cancelAdd.setOnClickListener {
                popupWindow.dismiss()
            }

            popupWindow.contentView.confirmAdd.setOnClickListener {
                var character = Character()
                character.name = popupWindow.contentView.name.text.toString()
                character.realm = popupWindow.contentView.spinnerRealm.selectedItem.toString()
                character.region =
                    popupWindow.contentView.spinnerRegion.selectedItem.toString().toLowerCase()
                // Instantiate the RequestQueue.
                var url = "https://" +
                        character.region +
                        ".api.blizzard.com/profile/wow/character/" +
                        character.realm.toLowerCase().replace("-", "").replace(" ", "-")
                            .replace("'", "") +
                        "/" +
                        character.name.toLowerCase() +
                        "?namespace=profile-" +
                        character.region +
                        "&locale=en_GB&access_token=" +
                        AuthKey.token

                // Request a string response from the provided URL.
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        val gson = Gson()
                        var apiCharacter = gson.fromJson(response, CharacterInfo::class.java)
                        Log.v("POPUP", apiCharacter.toString())
                        character.name = apiCharacter.name
                        character.guild = apiCharacter.guild.name

                        if (apiCharacter.faction.type == "HORDE")
                            character.faction = Faction.HORDE
                        else
                            character.faction = Faction.ALLIANCE

                        character.level = apiCharacter.level




                        if (CharacterCollection.characters.size == 0) {
                            character.isMain = true
                        }

                        CharacterCollection.characters.add(character)
                        CharacterCollection.characters.sort()

                        val bundle = Bundle()
                        bundle.putInt("charPos", CharacterCollection.characters.indexOf(character))
                        setFragmentResult("getMounts", bundle)

                        adapter.notifyItemInserted(CharacterCollection.characters.indexOf(character))
                        getImages(queue, CharacterCollection.characters.indexOf(character))

                        CharacterCollection.saveCharacterList((activity as MainActivity?)!!)

                        popupWindow.dismiss()

                    },
                    {
                        Log.v("CHARACTER", "GET didn't work!")
                        Toast.makeText(context, "Character doesn't exist", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
                // Add the request to the RequestQueue.
                queue.add(stringRequest)

            }

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
        var characterMedia = gson.fromJson(json, CharacterMedia::class.java)
        return characterMedia.avatar_url
    }

    fun getImages(queue: RequestQueue, position: Int = 0) {

        var rangeUpper = CharacterCollection.characters.size - 1
        var rangeLower = 0

        if (position != 0) {
            rangeUpper = position
            rangeLower = position
        }

        for (i in rangeUpper downTo rangeLower) {
            // Instantiate the RequestQueue.
            var url = "https://" +
                    CharacterCollection.characters[i].region +
                    ".api.blizzard.com/profile/wow/character/" +
                    CharacterCollection.characters[i].realm.toLowerCase().replace("-", "")
                        .replace(" ", "-").replace("'", "") +
                    "/" +
                    CharacterCollection.characters[i].name.toLowerCase() +
                    "/character-media?namespace=profile-" +
                    CharacterCollection.characters[i].region +
                    "&locale=en_GB&access_token=" +
                    AuthKey.token

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    CharacterCollection.characters[i].imageUrl = convertJson(response)
                    adapter.notifyItemChanged(i)
                    CharacterCollection.saveCharacterList((activity as MainActivity?)!!)

                },
                {
                    Log.v("JSON", "That didn't work!")
                }
            )
            // Add the request to the RequestQueue.
            queue.add(stringRequest)

        }
    }
}