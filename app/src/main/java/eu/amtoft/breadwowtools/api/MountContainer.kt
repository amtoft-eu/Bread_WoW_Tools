package eu.amtoft.breadwowtools.api

class MountContainer {
    //val _links: Links? = null
    var mounts: ArrayList<MountFromAPI> = ArrayList()

    inner class MountFromAPI {
        var mount: Mount = Mount()
        var is_usable: Boolean = true
    }
}