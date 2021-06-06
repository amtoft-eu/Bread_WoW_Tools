package eu.amtoft.breadwowtools.characters

import android.content.Context
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import eu.amtoft.breadwowtools.*
import eu.amtoft.breadwowtools.api.CharacterInfo
import eu.amtoft.breadwowtools.mounts.MountCollection
import kotlinx.android.synthetic.main.character_item.view.*
import kotlinx.android.synthetic.main.fragment_character.view.*
import kotlinx.android.synthetic.main.mount_item.view.*
import kotlinx.android.synthetic.main.popup_add_char.view.*
import kotlinx.android.synthetic.main.popup_remove_char.view.*

class CharacterAdapter(private val characters: ArrayList<Character>, private val activity: MainActivity) :
    Adapter<CharacterAdapter.CharacterHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflatedView = parent.inflate(R.layout.character_item, false)
        return CharacterHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val item = characters[position]
        holder.bindCharacter(item)

        holder.itemView.setOnLongClickListener{
            Log.d("CHARACTER", "Long click")
            // Initialize a new layout inflater instance
            val inflater: LayoutInflater =
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_remove_char, null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Set an elevation for the popup window
            popupWindow.elevation = 10.0F

            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.LEFT
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.LEFT
            popupWindow.exitTransition = slideOut

            TransitionManager.beginDelayedTransition(activity.findViewById(R.id.root_linear_layout))
            popupWindow.showAtLocation(
                activity.findViewById(R.id.root_linear_layout), // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
            popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            popupWindow.isFocusable = true
            popupWindow.isOutsideTouchable = false
            popupWindow.update()
            popupWindow.contentView.confirmRemoveText.text = activity.getString(R.string.remove_this_char, item.name)


            popupWindow.contentView.cancelRemove.setOnClickListener {
                popupWindow.dismiss()
            }

            popupWindow.contentView.confirmRemove.setOnClickListener {

                CharacterCollection.characters.removeAt(position)
                if (item.isMain){
                    if (CharacterCollection.characters.isNotEmpty()){
                        CharacterCollection.characters[0].isMain = true
                        notifyDataSetChanged()
                    }
                }

                MountCollection.unknownMounts.forEach {
                    it.checkedList.removeAt(position)
                }
                CharacterCollection.saveCharacterList(activity)
                MountCollection.saveMountList(activity)

                notifyItemRemoved(position)
                popupWindow.dismiss()
            }
            return@setOnLongClickListener true
        }
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

            if (character.isMain)
                view.mainStar.visibility = VISIBLE
            else
                view.mainStar.visibility = GONE

            if (character.paddingVertical == 0 && character.paddingHorizontal == 0){
                character.paddingVertical = (1400 * (0.5 - Math.random())).toInt()
                character.paddingHorizontal = (500 * (Math.random())).toInt()
            }

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
                else -> view.characterBackground.setBackgroundResource(R.drawable.texture2)
            }

            if (character.paddingVertical < 0){
                view.characterBackground.setPadding(character.paddingHorizontal, -character.paddingVertical,0,0)
            }
            else {
                view.characterBackground.setPadding(0, 0, character.paddingHorizontal, character.paddingVertical)
            }
        }

        companion object {
            private val CHARACTER_KEY = "CHARACTER"
        }

    }
}