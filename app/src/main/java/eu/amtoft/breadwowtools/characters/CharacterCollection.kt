package eu.amtoft.breadwowtools.characters

import android.content.SharedPreferences
import com.google.gson.Gson
import eu.amtoft.breadwowtools.MainActivity

object CharacterCollection {
    var characters: ArrayList<Character> = ArrayList()

    fun saveCharacterList(activity: MainActivity){
        val gson = Gson()
        val sharedPrefChar: SharedPreferences = activity.getSharedPreferences("CHARACTERS", 0)
        sharedPrefChar.edit().putString("CHARACTERS", gson.toJson(characters)).apply()
    }
}