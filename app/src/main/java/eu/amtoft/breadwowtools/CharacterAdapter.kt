package eu.amtoft.breadwowtools

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterAdapter(private val characters: ArrayList<Character>) : Adapter<CharacterAdapter.CharacterHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflatedView = parent.inflate(R.layout.character_item, false)
        return CharacterHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val item = characters[position]
        holder.bindCharacter(item)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CharacterHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {

        private var view: View = v
        private var character: Character? = null

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindCharacter(character: Character) {
            this.character = character
            view.characterName.text = character.name
            view.characterImage.setImageResource(character.icon)
            view.characterGuild.text = character.guild
            view.characterLevel.text = character.level.toString()

            when (character.faction){
                Faction.ALLIANCE -> view.characterBackground.setImageResource(R.drawable.texture_gradient_alliance)
                Faction.HORDE -> view.characterBackground.setImageResource(R.drawable.texture_gradient_horde)
                else -> view.characterBackground.setImageResource(R.drawable.texture2)
            }

        }

        companion object {
            private val CHARACTER_KEY = "CHARACTER"
        }

    }
}