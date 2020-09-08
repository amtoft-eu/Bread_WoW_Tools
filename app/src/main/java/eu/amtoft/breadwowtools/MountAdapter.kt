package eu.amtoft.breadwowtools

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.mount_item.view.*

class MountAdapter(private val mounts: ArrayList<Mount>) : Adapter<MountAdapter.MountHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountHolder {
        val inflatedView = parent.inflate(R.layout.mount_item, false)
        var holder = MountHolder(inflatedView)
//        holder.setIsRecyclable(false)
        return holder

    }

    override fun onBindViewHolder(holder: MountHolder, position: Int) {
        val item = mounts[position]
        holder.bindMount(item)
    }

    override fun getItemCount(): Int {
        return mounts.size
    }

    class MountHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {

        private var view: View = v
        private var mount: Mount? = null
        private lateinit var adapter: CharacterSubAdapter
        private lateinit var linearLayoutManager: LinearLayoutManager

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindMount(mount: Mount) {
            this.mount = mount
            view.mountName.text = mount.name
            view.mountImage.setImageResource(mount.icon)
            view.mountLocation.text = mount.location
            view.mountDroprate.text = "~%.1f %%".format(mount.droprate)
            when (mount.expansion){
                Expansion.VANNILA -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_01_vanilla)
                Expansion.TBC -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_02_tbc)
                Expansion.WOTLK -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_03_wotlk)
                Expansion.CATA -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_04_cata)
                Expansion.MOP -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_05_mop)
                Expansion.WOD -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_06_wod)
                Expansion.LEGION -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_07_legion)
                Expansion.BFA -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_08_bfa)
                Expansion.SL -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_09_sl)
                else -> view.mountBackground.setBackgroundResource(R.drawable.texture_gradient_01_vanilla)
            }
            view.setOnClickListener {
                if (it.sub_item.visibility == VISIBLE) {
                    it.sub_item.visibility = GONE
                }
                else {
                    it.sub_item.visibility = VISIBLE
                }
            }

            val characterRecycler = view.findViewById(R.id.sub_item) as RecyclerView
            linearLayoutManager = LinearLayoutManager(view.context)

            characterRecycler.layoutManager = linearLayoutManager

            var characterList: ArrayList<Character> = ArrayList()
            var hordePH = Character()
            hordePH.faction = Faction.HORDE
            var alliancePH = Character()
            alliancePH.faction = Faction.ALLIANCE
            characterList.add(alliancePH)
            characterList.add(hordePH)
            adapter = CharacterSubAdapter(characterList)
            characterRecycler.adapter = adapter

        }

        companion object {
            private val MOUNT_KEY = "MOUNT"
        }

    }
}