package eu.amtoft.breadwowtools

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso
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
            view.characterGuild.text = character.guild
            view.characterRealm.text = character.realm
            view.characterLevel.text = character.level.toString()
            if (character.imageUrl != ""){
                Picasso.get().load(character.imageUrl).into(view.characterImage)
            }
            when (character.faction){
                Faction.ALLIANCE -> view.characterBackground.setBackgroundResource(R.drawable.texture_gradient_alliance)
                Faction.HORDE -> view.characterBackground.setBackgroundResource(R.drawable.texture_gradient_horde)
                else -> view.characterBackground.setBackgroundResource(R.drawable.texture2)
            }

        }

        companion object {
            private val CHARACTER_KEY = "CHARACTER"
        }

    }
}