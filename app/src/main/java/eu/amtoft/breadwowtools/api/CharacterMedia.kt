package eu.amtoft.breadwowtools.api

class CharacterMedia {
    var assets: Array<Asset> = arrayOf(Asset())


    inner class Asset {
        var key: String = ""
        var value: String = ""
    }
}