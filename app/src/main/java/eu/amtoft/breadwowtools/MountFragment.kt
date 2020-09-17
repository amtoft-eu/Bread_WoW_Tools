package eu.amtoft.breadwowtools

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.api.MountContainer

class MountFragment : Fragment() {

    private lateinit var adapter: MountAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "CHARACTERS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mount, container, false)



        val mountRecycler = rootView.findViewById(R.id.mountRecycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(context)

        mountRecycler.layoutManager = linearLayoutManager

        val queue = Volley.newRequestQueue(context)

        CharacterCollection.characters.forEach() {
            // Instantiate the RequestQueue.
            if (it.isMain) {
                var url = "https://" +
                        it.region +
                        ".api.blizzard.com/profile/wow/character/" +
                        it.realm.toLowerCase().replace("-", "").replace(" ", "-").replace("'", "") +
                        "/" +
                        it.name.toLowerCase() +
                        "/collections/mounts?namespace=profile-" +
                        it.region +
                        "&locale=en_GB&access_token=USv96NMArDs9veh9NcvM47BJNePx4Q3TjD"

                // Request a string response from the provided URL.
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        // Display the first 500 characters of the response string.
                        convertJson(response)
                    },
                    {
                        Log.v("JSON", "That didn't work!")
                    }
                )
                // Add the request to the RequestQueue.
                queue.add(stringRequest)
            }
        }

        adapter = MountAdapter(ObtainableMounts.unknownMounts)
        mountRecycler.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = MountFragment().apply { arguments = Bundle().apply {} }
    }

    fun convertJson(json: String) {
        var gson = Gson()
        var mountContainer = gson.fromJson<MountContainer>(json, MountContainer::class.java)
        ObtainableMounts.mounts.forEach() { obtainable ->
            var found = false
            mountContainer.mounts.forEach() { known ->
                if (known.mount.id == obtainable.id) {
                    Log.v("MOUNT", "Found matching ID: " + obtainable.id)
                    found = true
                }
            }
            if (!found) {

                var foundInUnknown = false
                ObtainableMounts.unknownMounts.forEach() { unknownMount ->
                    if (unknownMount.id == obtainable.id){
                        foundInUnknown = true
                    }
                }
                if (!foundInUnknown){
                    ObtainableMounts.unknownMounts.add(obtainable)
                }

            }
        }
        adapter.notifyDataSetChanged()
    }

}