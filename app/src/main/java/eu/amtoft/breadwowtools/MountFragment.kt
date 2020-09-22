package eu.amtoft.breadwowtools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.api.MountContainer
import java.util.*

class MountFragment : Fragment() {

    private lateinit var adapter: MountAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResultListener("mountUpdate") { _, _ ->
            Log.v("MOUNT", "setFragmentResultListener1")
            adapter.notifyDataSetChanged()
        }
        setFragmentResultListener("getMounts") { a, bundle ->
            Log.v("MOUNT", "setFragmentResultListener2")
            getMounts(bundle.getInt("charPos", -1))
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

        getMounts(-1)

        adapter = MountAdapter(MountCollection.unknownMounts, activity as MainActivity)
        mountRecycler.adapter = adapter



        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = MountFragment().apply { arguments = Bundle().apply {} }
    }

    fun getMounts(charPos: Int) {
        Log.v("MOUNT", "In getMounts")
        val queue = Volley.newRequestQueue(activity as MainActivity)
        CharacterCollection.characters.forEach { character ->
            if (character.isMain) {
                var url = "https://" +
                        character.region +
                        ".api.blizzard.com/profile/wow/character/" +
                        character.realm.toLowerCase().replace("-", "").replace(" ", "-").replace("'", "") +
                        "/" +
                        character.name.toLowerCase() +
                        "/collections/mounts?namespace=profile-" +
                        character.region +
                        "&locale=en_GB&access_token=USSKcgwq7pTpiYKyKAV2I8Ub2pgIeV4k0r"
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        convertJson(response)
                        if (charPos >= 0)
                        MountCollection.unknownMounts.forEach {
                                it.checkedList.add(charPos, false)
                        }
                    },
                    {
                        Log.v("MOUNT", "GET didn't work!")
                    }
                )
                queue.add(stringRequest)
            }
        }
    }

    private fun convertJson(json: String) {
        val gson = Gson()
        val mountContainer = gson.fromJson(json, MountContainer::class.java)
        if (MountCollection.unknownMounts.size == 0)
            initialMountFiltering(mountContainer)
        else
            updateMounts(mountContainer)

    }

    private fun initialMountFiltering(mountContainer: MountContainer) {
        MountCollection.mounts.forEach { obtainable ->
            var found = false
            mountContainer.mounts.forEach { known ->
                if (known.mount.id == obtainable.id || (known.mount.id == 286 && obtainable.id == 287) || (known.mount.id == 287 && obtainable.id == 286)) {
                    found = true
                }
            }
            if (!found) {

                var foundInUnknown = false
                MountCollection.unknownMounts.forEach { unknownMount ->
                    if (unknownMount.id == obtainable.id) {
                        foundInUnknown = true
                    }
                }
                if (!foundInUnknown) {
                    MountCollection.unknownMounts.add(obtainable)
                }
            }
        }
        Log.v("MOUNT", "setFragmentResult")
        setFragmentResult("mountUpdate", Bundle())
        MountCollection.unknownMounts.sort()
        MountCollection.saveMountList(activity as MainActivity)
    }

    private fun updateMounts(mountContainer: MountContainer) {

        MountCollection.mounts.forEach { obtainable ->
            var unknown = false
            var mount = Mount()
            MountCollection.unknownMounts.forEach { unknownMount ->
                if (obtainable.id == unknownMount.id) {
                    unknown = true
                    mount = unknownMount
                    unknownMount.icon = obtainable.icon
                }
            }
            var known = false
            mountContainer.mounts.forEach { knownMount ->
                if (obtainable.id == knownMount.mount.id)
                    known = true
            }

            if (unknown && known) {
                var pos = MountCollection.unknownMounts.indexOf(mount)
                MountCollection.unknownMounts.remove(mount)
                adapter.notifyItemRemoved(pos)

            }
            if (!unknown && !known && obtainable.id != 286 && obtainable.id != 287) {
                CharacterCollection.characters.forEach {
                    obtainable.checkedList.add(false)
                }
                MountCollection.unknownMounts.add(obtainable)
                adapter.notifyItemInserted(MountCollection.unknownMounts.size-1)
            }
        }
        MountCollection.unknownMounts.sort()
        MountCollection.saveMountList(activity as MainActivity)
    }


}