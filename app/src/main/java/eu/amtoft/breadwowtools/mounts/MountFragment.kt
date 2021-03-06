package eu.amtoft.breadwowtools.mounts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.amtoft.breadwowtools.MainActivity
import eu.amtoft.breadwowtools.R

class MountFragment : Fragment() {

    private lateinit var adapter: MountAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResultListener("mountUpdate") { _, _ ->
            Log.v("MOUNT", "Handling mountupdate")
            adapter.notifyDataSetChanged()
        }
        setFragmentResultListener("getMounts") { _, bundle ->
            Log.v("MOUNT", "Handling get mount request")
            MountCollection.getMounts(bundle.getInt("charPos", -1), this)
        }
        setFragmentResultListener("removeMount") { _, bundle ->
            Log.v("MOUNT", "Handling mount removed call")
            var mountPos = bundle.getInt("mountPos", -1)
            if (mountPos >= 0) {
                adapter.notifyItemRemoved(mountPos)
            }
        }
        setFragmentResultListener("addMount") { _, bundle ->
            Log.v("MOUNT", "Handling mount added call")
            var mountPos = bundle.getInt("mountPos", -1)
            if (mountPos >= 0) {
                adapter.notifyItemInserted(mountPos)
            }
        }
        setFragmentResultListener("removeChar") { _, bundle ->
            Log.v("MOUNT", "Handling Char removed call")
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

        MountCollection.getMounts(-1, this)

        adapter = MountAdapter(MountCollection.unknownMounts, activity as MainActivity)
        mountRecycler.adapter = adapter



        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = MountFragment().apply { arguments = Bundle().apply {} }
    }


}