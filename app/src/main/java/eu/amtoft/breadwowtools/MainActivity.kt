package eu.amtoft.breadwowtools

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.amtoft.breadwowtools.characters.Character
import eu.amtoft.breadwowtools.characters.CharacterCollection
import eu.amtoft.breadwowtools.mounts.Mount
import eu.amtoft.breadwowtools.mounts.MountCollection
import io.sentry.Sentry
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val OPTION_PREF_NAME = "OPTIONS"
    private val CHAR_PREF_NAME = "CHARACTERS"
    private val MOUNT_PREF_NAME = "MOUNTS"
    private val AUTH_PREF_NAME = "TOKEN_DATA"
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefTheme: SharedPreferences = getSharedPreferences(OPTION_PREF_NAME, PRIVATE_MODE)
        val sharedPrefChar: SharedPreferences = getSharedPreferences(CHAR_PREF_NAME, PRIVATE_MODE)
        val sharedPrefMount: SharedPreferences = getSharedPreferences(MOUNT_PREF_NAME, PRIVATE_MODE)
        val sharedPrefToken: SharedPreferences = getSharedPreferences(AUTH_PREF_NAME, PRIVATE_MODE)

        AuthKey.token = sharedPrefToken.getString("TOKEN", "")!!
        AuthKey.timeOfDeath = sharedPrefToken.getLong("DEATH", 0)

        if (AuthKey.token == "" || AuthKey.timeOfDeath < System.currentTimeMillis()){
            AuthKey.getToken(this)
        }

        if (sharedPrefTheme.getBoolean("THEME", false)) {
            setTheme(R.style.HordeTheme)
        } else {
            setTheme(R.style.AllianceTheme)
            val editor = sharedPrefTheme.edit()
            editor.putBoolean(OPTION_PREF_NAME, false)
            editor.apply()
        }

        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val receiverIntent : Intent = Intent(this, Alarm::class.java)
        val pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 0, receiverIntent, 0)

        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val currentTime = System.currentTimeMillis()
//        alarmManager.set(AlarmManager.RTC, currentTime  + 10000, pendingIntent)

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

    override fun onPause() {

        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "BreadReminderChannel"
            val description: String = "Channel for Bread notifications"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel("notifyBread", name, importance)
            channel.description = description

            val notificationManager : NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

    }

}