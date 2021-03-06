package eu.amtoft.breadwowtools.characters

import eu.amtoft.breadwowtools.Faction

class Character : Comparable<Character> {
    var name: String = "Amtoft"
    var level: Int = 60
    var guild: String = "Bread"
    var realm: String = "Argent Dawn"
    var region: String = "eu"
    var faction: Faction = Faction.UNKNOWN
    var imageUrl: String = ""
    var isMain: Boolean = false
    var paddingVertical = 0
    var paddingHorizontal = 0

    override fun compareTo(other: Character): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return name
    }
}