package eu.amtoft.breadwowtools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MountFragment : Fragment() {

    private lateinit var adapter: MountAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

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


        var vanillaPH = Mount()
        vanillaPH.expansion = Expansion.VANNILA
        var tbcPH = Mount()
        tbcPH.expansion = Expansion.TBC
        var wotlkPH = Mount()
        wotlkPH.expansion = Expansion.WOTLK
        var cataPH = Mount()
        cataPH.expansion = Expansion.CATA
        var mopPH = Mount()
        mopPH.expansion = Expansion.MOP
        var wodPH = Mount()
        wodPH.expansion = Expansion.WOD
        var legionPH = Mount()
        legionPH.expansion = Expansion.LEGION
        var bfaPH = Mount()
        bfaPH.expansion = Expansion.BFA
        var slPH = Mount()
        slPH.expansion = Expansion.SL

        var mountList: ArrayList<Mount> = ArrayList()
        mountList.add(vanillaPH)
        mountList.add(tbcPH)
        mountList.add(wotlkPH)
        mountList.add(cataPH)
        mountList.add(mopPH)
        mountList.add(wodPH)
        mountList.add(legionPH)
        mountList.add(bfaPH)
        mountList.add(slPH)
        mountList.add(vanillaPH)
        mountList.add(tbcPH)
        mountList.add(wotlkPH)
        mountList.add(cataPH)
        mountList.add(mopPH)
        mountList.add(wodPH)
        mountList.add(legionPH)
        mountList.add(bfaPH)
        mountList.add(slPH)


        adapter = MountAdapter(mountList)
        mountRecycler.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MountFragment().apply {
                arguments = Bundle().apply {
                }

            }
    }

}