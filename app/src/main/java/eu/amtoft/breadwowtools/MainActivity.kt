package eu.amtoft.breadwowtools

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import eu.amtoft.breadwowtools.api.MountContainer
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "THEME"
    private val clientId = "5014d622da2c46d2aa3e720cb7e57b2d"
    private val clientSecret = "cbO3Z7Oly8vNzP56SJuuA5ABZdyKS4Ym"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        if (sharedPref.getBoolean(PREF_NAME, false)) {
            setTheme(R.style.HordeTheme)
        } else {
            setTheme(R.style.AllianceTheme)
            val editor = sharedPref.edit()
            editor.putBoolean(PREF_NAME, false)
            editor.apply()
        }

        setContentView(R.layout.activity_main)


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