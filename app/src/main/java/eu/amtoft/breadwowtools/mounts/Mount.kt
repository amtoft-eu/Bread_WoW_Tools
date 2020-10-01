package eu.amtoft.breadwowtools.mounts

import eu.amtoft.breadwowtools.Expansion
import eu.amtoft.breadwowtools.R
import eu.amtoft.breadwowtools.Reset

class Mount(
    var id: Int = 0,
    var name: String = "",
    var expansion: Expansion = Expansion.UNKNOWN,
    var location: String = "",
    var droprate: Double = 0.0,
    var reset: Reset = Reset.UNKNOWN,
    var icon: Int = R.drawable.mount_swift_white_hawkstrider
)  : Cloneable, Comparable<Mount>  {
    var expanded: Boolean = false
    var animationDone = true
    var checkedList: ArrayList<Boolean> = ArrayList()

    public override fun clone(): Mount {
        val mount = Mount(id, name, expansion, location, droprate, reset, icon)
        mount.checkedList = checkedList
        return mount
    }

    override fun compareTo(other: Mount): Int {
        return (expansion.ordinal * 100000 + id) - (other.expansion.ordinal * 100000 + other.id)
    }

    override fun toString(): String {
        return name
    }
}