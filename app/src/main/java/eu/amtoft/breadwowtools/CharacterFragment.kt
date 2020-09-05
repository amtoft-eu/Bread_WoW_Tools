package eu.amtoft.breadwowtools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterFragment : Fragment() {

    private lateinit var adapter: CharacterAdapter
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
        val rootView = inflater.inflate(R.layout.fragment_character, container, false)

        val characterRecycler = rootView.findViewById(R.id.characterRecycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(context)

        characterRecycler.layoutManager = linearLayoutManager

        var characterList: ArrayList<Character> = ArrayList()
        var hordePH = Character()
        hordePH.faction = Faction.HORDE
        var alliancePH = Character()
        alliancePH.faction = Faction.ALLIANCE
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)
        characterList.add(alliancePH)
        characterList.add(hordePH)



        adapter = CharacterAdapter(characterList)
        characterRecycler.adapter = adapter

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
}