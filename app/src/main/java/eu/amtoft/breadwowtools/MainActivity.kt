package eu.amtoft.breadwowtools

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "THEME"
    private val CHAR_PREF_NAME = "CHARACTERS"
    private val clientId = "5014d622da2c46d2aa3e720cb7e57b2d"
    private val clientSecret = "cbO3Z7Oly8vNzP56SJuuA5ABZdyKS4Ym"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefTheme: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val sharedPrefChar: SharedPreferences = getSharedPreferences(CHAR_PREF_NAME, PRIVATE_MODE)

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
        if (charactersJson != "") {
            val gson = Gson()
            val itemType = object : TypeToken<ArrayList<Character>>() {}.type
            CharacterCollection.characters =
                gson.fromJson<ArrayList<Character>>(charactersJson, itemType)
        }
        else {
            CharacterCollection.characters.add(Character())
            CharacterCollection.characters[0].faction = Faction.ALLIANCE
            CharacterCollection.characters[0].isMain = true
            /*button.setOnClickListener {
                Log.v("MAIN", "In on click listener")
                // Initialize a new layout inflater instance
                val inflater: LayoutInflater =
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val view = inflater.inflate(R.layout.popup_add_char, null)

                // Initialize a new instance of popup window
                val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Slide()
                    slideIn.slideEdge = Gravity.TOP
                    popupWindow.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Slide()
                    slideOut.slideEdge = Gravity.BOTTOM
                    popupWindow.exitTransition = slideOut

                }

                popupWindow.isFocusable = true
                popupWindow.update()

                TransitionManager.beginDelayedTransition(root_layout)
                popupWindow.showAtLocation(
                    root_layout, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
                )
            }*/
        }



        val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(this, 3)

        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(1, false)
        bottomNav.selectedItemId = R.id.button_2

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