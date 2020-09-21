package eu.amtoft.breadwowtools

class Mount(
    var id: Int = 0,
    var name: String = "",
    var expansion: Expansion = Expansion.UNKNOWN,
    var location: String = "",
    var droprate: Double = 0.0,
    var reset: Reset = Reset.UNKNOWN,
    var icon: Int = R.drawable.mount_swift_white_hawkstrider
) {
    var expanded: Boolean = false
    var checkedList: ArrayList<Boolean> = ArrayList()

}