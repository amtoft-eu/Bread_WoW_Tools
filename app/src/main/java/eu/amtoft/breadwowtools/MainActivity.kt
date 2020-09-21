package eu.amtoft.breadwowtools

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.amtoft.breadwowtools.api.MountContainer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "THEME"
    private val CHAR_PREF_NAME = "CHARACTERS"
    private val MOUNT_PREF_NAME = "MOUNTS"
    private val clientId = "5014d622da2c46d2aa3e720cb7e57b2d"
    private val clientSecret = "cbO3Z7Oly8vNzP56SJuuA5ABZdyKS4Ym"
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefTheme: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val sharedPrefChar: SharedPreferences = getSharedPreferences(CHAR_PREF_NAME, PRIVATE_MODE)
        val sharedPrefMount: SharedPreferences = getSharedPreferences(MOUNT_PREF_NAME, PRIVATE_MODE)

        if (sharedPrefTheme.getBoolean(PREF_NAME, false)) {
            setTheme(R.style.HordeTheme)
        } else {
            setTheme(R.style.AllianceTheme)
            val editor = sharedPrefTheme.edit()
            editor.putBoolean(PREF_NAME, false)
            editor.apply()
        }

        setContentView(R.layout.activity_main)

        val charactersJson = sharedPrefChar.getString(CHAR_PREF_NAME, "")
        val gson = Gson()
        if (charactersJson != "") {
            val itemType = object : TypeToken<ArrayList<Character>>() {}.type
            CharacterCollection.characters =
                gson.fromJson<ArrayList<Character>>(charactersJson, itemType)
        }

        val mountsJson = sharedPrefMount.getString(MOUNT_PREF_NAME, "")
        if (mountsJson != "") {
            Log.v("MAIN", "Mounts found in shared pref")
            val itemType = object : TypeToken<ArrayList<Mount>>() {}.type
            MountCollection.unknownMounts =
                gson.fromJson<ArrayList<Mount>>(mountsJson, itemType)
        }

       viewPagerAdapter = ViewPagerAdapter(this, 3)

        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(1, false)
        bottomNav.selectedItemId = R.id.button_2

        viewPager.isUserInputEnabled = false
        viewPager.registerOnPageChangeCallback(PageChange { })
        bottomNav.setOnNavigationItemSelectedListener(ItemSelect { })

    }

    inner class PageChange(private val listener: (Int) -> Unit) :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                0 -> bottomNav.selectedItemId = R.id.button_1
                1 -> bottomNav.selectedItemId = R.id.button_2
                2 -> bottomNav.selectedItemId = R.id.button_3
            }
        }
    }

    inner class ItemSelect(private val listener: (Int) -> Unit) :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.button_1 -> viewPager.currentItem = 0
                R.id.button_2 -> viewPager.currentItem = 1
                R.id.button_3 -> viewPager.currentItem = 2

            }
            return true
        }

    }




}