package eu.amtoft.breadwowtools.characters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso
import eu.amtoft.breadwowtools.*
import eu.amtoft.breadwowtools.mounts.Mount
import eu.amtoft.breadwowtools.mounts.MountCollection
import kotlinx.android.synthetic.main.character_item.view.characterBackground
import kotlinx.android.synthetic.main.character_item.view.characterImage
import kotlinx.android.synthetic.main.character_item.view.characterName
import kotlinx.android.synthetic.main.character_sub_item.view.*

class CharacterSubAdapter(
    private val characters: ArrayList<Character>,
    private val mount: Mount,
    private val activity: MainActivity
) : Adapter<CharacterSubAdapter.CharacterHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflatedView = parent.inflate(R.layout.character_sub_item, false)
        return CharacterHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val item = characters[position]
        holder.bindCharacter(item, position)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {

        private var view: View = v
        private var character: Character? = null

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindCharacter(character: Character, position: Int) {
            this.character = character
            view.characterName.text = character.name

            when (character.faction) {
                Faction.ALLIANCE -> {
                    view.characterBackground.setImageResource(R.drawable.texture_gradient_alliance)
                    if (character.imageUrl != "")
                        Picasso.get().load(character.imageUrl)
                            .placeholder(R.drawable.alliance_fallback).into(view.characterImage)
                    else
                        Picasso.get().load(R.drawable.alliance_fallback).into(view.characterImage)
                }
                Faction.HORDE -> {
                    view.characterBackground.setImageResource(R.drawable.texture_gradient_horde)
                    if (character.imageUrl != "")
                        Picasso.get().load(character.imageUrl)
                            .placeholder(R.drawable.horde_fallback).into(view.characterImage)
                    else
                        Picasso.get().load(R.drawable.horde_fallback).into(view.characterImage)
                }
                else -> view.characterBackground.setImageResource(R.drawable.texture2)
            }

            view.checkbox.isChecked = mount.checkedList[position]

            view.checkbox.setOnCheckedChangeListener { _, isChecked ->
                mount.checkedList[position] = isChecked
                MountCollection.saveMountList(activity)
            }
            if (character.paddingVertical < 0) {
                view.characterBackground.setPadding(
                    character.paddingHorizontal,
                    -character.paddingVertical,
                    0,
                    0
                )
            } else {
                view.characterBackground.setPadding(
                    0,
                    0,
                    character.paddingHorizontal,
                    character.paddingVertical
                )
            }

            view.setOnClickListener {
                view.checkbox.isChecked = !view.checkbox.isChecked
            }

        }

    }
}