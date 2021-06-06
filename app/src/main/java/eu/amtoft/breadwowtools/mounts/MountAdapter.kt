package eu.amtoft.breadwowtools.mounts

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import eu.amtoft.breadwowtools.*
import eu.amtoft.breadwowtools.characters.CharacterCollection
import eu.amtoft.breadwowtools.characters.CharacterSubAdapter
import kotlinx.android.synthetic.main.mount_item.view.*


class MountAdapter(private val mounts: ArrayList<Mount>, private val activity: MainActivity) :
    Adapter<MountAdapter.MountHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountHolder {
        val inflatedView = parent.inflate(R.layout.mount_item, false)
        return MountHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: MountHolder, position: Int) {
        val mount = mounts[position]

        holder.itemView.setOnClickListener {
            mount.expanded = !mount.expanded
            mount.animationDone = false
            notifyItemChanged(position)


        }
        holder.bindMount(mount)
    }

    override fun getItemCount(): Int {
        return mounts.size
    }

    inner class MountHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {

        private var view: View = v
        private var mount: Mount? = null
        private lateinit var adapter: CharacterSubAdapter
        private lateinit var linearLayoutManager: LinearLayoutManager

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        @SuppressLint("SetTextI18n")
        fun bindMount(mount: Mount) {
            this.mount = mount


            if (mount.checkedList.size < 3) {
                mount.expanded = true
                view.expandArrow.visibility = GONE
            } else {
                view.expandArrow.visibility = VISIBLE
            }
            if (mount.animationDone) {
                view.expandArrow.rotation = if (mount.expanded) 180F else 0F
            } else {
                if (mount.expanded) {
                    view.expandArrow.rotation = 0f
                    view.expandArrow.animate().setDuration(500).rotation(180F)
                } else {
                    view.expandArrow.rotation = 180f
                    view.expandArrow.animate().setDuration(500).rotation(0F)
                }
                mount.animationDone = true
            }

            view.sub_item.visibility = if (mount.expanded) VISIBLE else GONE
            view.mountName.text = mount.name
            view.mountImage.setImageResource(mount.icon)
            view.mountLocation.text = mount.location

            if (mount.droprate != 0.0)
                view.mountDroprate.text = "≈ %.1f %%".format(mount.droprate)
            else
                view.mountDroprate.text = ""

            when (mount.expansion) {
                Expansion.VANILLA -> view.mountBackground.setImageResource(R.drawable.texture_gradient_01_vanilla)
                Expansion.TBC -> view.mountBackground.setImageResource(R.drawable.texture_gradient_02_tbc)
                Expansion.WOTLK -> view.mountBackground.setImageResource(R.drawable.texture_gradient_03_wotlk)
                Expansion.CATA -> view.mountBackground.setImageResource(R.drawable.texture_gradient_04_cata)
                Expansion.MOP -> view.mountBackground.setImageResource(R.drawable.texture_gradient_05_mop)
                Expansion.WOD -> view.mountBackground.setImageResource(R.drawable.texture_gradient_06_wod)
                Expansion.LEGION -> view.mountBackground.setImageResource(R.drawable.texture_gradient_07_legion)
                Expansion.BFA -> view.mountBackground.setImageResource(R.drawable.texture_gradient_08_bfa)
                Expansion.SL -> view.mountBackground.setImageResource(R.drawable.texture_gradient_09_sl)
                else -> view.mountBackground.setImageResource(R.drawable.texture_gradient_01_vanilla)
            }

            val characterRecycler = view.findViewById(R.id.sub_item) as RecyclerView
            linearLayoutManager = LinearLayoutManager(view.context)
            characterRecycler.layoutManager = linearLayoutManager
            adapter = CharacterSubAdapter(CharacterCollection.characters, mount, activity)
            characterRecycler.adapter = adapter
            val itemDecoration = MyDividerItemDecorator(ContextCompat.getDrawable(activity, R.drawable.recycle_divider))
            characterRecycler.addItemDecoration(itemDecoration)

            if (mount.paddingVertical == 0 && mount.paddingHorizontal == 0){
                mount.paddingVertical = (1400 * (0.5 - Math.random())).toInt()
                mount.paddingHorizontal = (500 * (Math.random())).toInt()
            }
            if (mount.paddingVertical < 0){
               view.mountBackground.setPadding(mount.paddingHorizontal, -mount.paddingVertical,0,0)
            }
            else{
                view.mountBackground.setPadding(0,0,mount.paddingHorizontal, mount.paddingVertical)
            }
        }

    }
}