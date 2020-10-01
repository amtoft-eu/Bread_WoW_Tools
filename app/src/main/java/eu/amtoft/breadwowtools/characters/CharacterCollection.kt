package eu.amtoft.breadwowtools.characters

import android.app.Activity
import android.content.SharedPreferences
import com.google.gson.Gson

object CharacterCollection {
    var characters: ArrayList<Character> = ArrayList()

    fun saveCharacterList(activity: Activity){
        val gson = Gson()
        val sharedPrefChar: SharedPreferences = activity.getSharedPreferences("CHARACTERS", 0)
        sharedPrefChar.edit().putString("CHARACTERS", gson.toJson(characters)).apply()
    }
}