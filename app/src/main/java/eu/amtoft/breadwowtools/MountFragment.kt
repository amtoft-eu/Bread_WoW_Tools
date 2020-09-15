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

        var hordePH = Character()
        hordePH.faction = Faction.HORDE
        var alliancePH = Character()
        alliancePH.faction = Faction.ALLIANCE

        var vanillaPH = Mount()
        vanillaPH.expansion = Expansion.VANNILA
        vanillaPH.characterList.add(alliancePH)
        vanillaPH.characterList.add(hordePH)

        var tbcPH = Mount()
        tbcPH.expansion = Expansion.TBC
        tbcPH.characterList.add(alliancePH)
        tbcPH.characterList.add(hordePH)

        var wotlkPH = Mount()
        wotlkPH.expansion = Expansion.WOTLK
        wotlkPH.characterList.add(alliancePH)
        wotlkPH.characterList.add(hordePH)

        var cataPH = Mount()
        cataPH.expansion = Expansion.CATA
        cataPH.characterList.add(alliancePH)
        cataPH.characterList.add(hordePH)

        var mopPH = Mount()
        mopPH.expansion = Expansion.MOP
        mopPH.characterList.add(alliancePH)
        mopPH.characterList.add(hordePH)

        var wodPH = Mount()
        wodPH.expansion = Expansion.WOD
        wodPH.characterList.add(alliancePH)
        wodPH.characterList.add(hordePH)

        var legionPH = Mount()
        legionPH.expansion = Expansion.LEGION
        legionPH.characterList.add(alliancePH)
        legionPH.characterList.add(hordePH)

        var bfaPH = Mount()
        bfaPH.expansion = Expansion.BFA
        bfaPH.characterList.add(alliancePH)
        bfaPH.characterList.add(hordePH)

        var slPH = Mount()
        slPH.expansion = Expansion.SL
        slPH.characterList.add(alliancePH)
        slPH.characterList.add(hordePH)

        var vanillaPH2 = Mount()
        vanillaPH2.expansion = Expansion.VANNILA
        vanillaPH2.characterList.add(alliancePH)
        vanillaPH2.characterList.add(hordePH)

        var tbcPH2 = Mount()
        tbcPH2.expansion = Expansion.TBC
        tbcPH2.characterList.add(alliancePH)
        tbcPH2.characterList.add(hordePH)

        var wotlkPH2 = Mount()
        wotlkPH2.expansion = Expansion.WOTLK
        wotlkPH2.characterList.add(alliancePH)
        wotlkPH2.characterList.add(hordePH)

        var cataPH2 = Mount()
        cataPH2.expansion = Expansion.CATA
        cataPH2.characterList.add(alliancePH)
        cataPH2.characterList.add(hordePH)

        var mopPH2 = Mount()
        mopPH2.expansion = Expansion.MOP
        mopPH2.characterList.add(alliancePH)
        mopPH2.characterList.add(hordePH)

        var wodPH2 = Mount()
        wodPH2.expansion = Expansion.WOD
        wodPH2.characterList.add(alliancePH)
        wodPH2.characterList.add(hordePH)

        var legionPH2 = Mount()
        legionPH2.expansion = Expansion.LEGION
        legionPH2.characterList.add(alliancePH)
        legionPH2.characterList.add(hordePH)

        var bfaPH2 = Mount()
        bfaPH2.expansion = Expansion.BFA
        bfaPH2.characterList.add(alliancePH)
        bfaPH2.characterList.add(hordePH)

        var slPH2 = Mount()
        slPH2.expansion = Expansion.SL
        slPH2.characterList.add(alliancePH)
        slPH2.characterList.add(hordePH)

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
        mountList.add(vanillaPH2)
        mountList.add(tbcPH2)
        mountList.add(wotlkPH2)
        mountList.add(cataPH2)
        mountList.add(mopPH2)
        mountList.add(wodPH2)
        mountList.add(legionPH2)
        mountList.add(bfaPH2)
        mountList.add(slPH2)


        adapter = MountAdapter(mountList)
        mountRecycler.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = MountFragment().apply {arguments = Bundle().apply {}}
    }

}