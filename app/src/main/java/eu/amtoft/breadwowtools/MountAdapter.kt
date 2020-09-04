package eu.amtoft.breadwowtools

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.mount_item.view.*

class MountAdapter(private val mounts: ArrayList<Mount>) : Adapter<MountAdapter.MountHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountHolder {
        val inflatedView = parent.inflate(R.layout.mount_item, false)
        return MountHolder(inflatedView)

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

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindMount(mount: Mount) {
            this.mount = mount
            view.mountName.text = mount.name
            view.mountImage.setImageResource(mount.icon)
            view.mountLocation.text = mount.location
            view.mountDroprate.text = "~%.1f %%".format(mount.droprate)

        }

        companion object {
            private val MOUNT_KEY = "MOUNT"
        }

    }
}