package eu.amtoft.breadwowtools.api

class CharacterInfo{
    var faction: APIFaction = APIFaction()
    var level: Int = 0
    var guild: APIGuild = APIGuild()

    inner class APIFaction{
        var type: String = ""
    }

    inner class APIGuild{
        var name: String = ""
    }
}