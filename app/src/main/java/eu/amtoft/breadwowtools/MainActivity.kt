package eu.amtoft.breadwowtools

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val OPTION_PREF_NAME = "OPTIONS"
    private val CHAR_PREF_NAME = "CHARACTERS"
    private val MOUNT_PREF_NAME = "MOUNTS"
    private val AUTH_PREF_NAME = "TOKEN_DATA"
    lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var sharedPrefOption : SharedPreferences
    private lateinit var sharedPrefChar : SharedPreferences
    private lateinit var sharedPrefMount : SharedPreferences
    private lateinit var sharedPrefToken : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val receiver = ComponentName(this, BootReciever::class.java)

        this.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        sharedPrefOption = getSharedPreferences(OPTION_PREF_NAME, PRIVATE_MODE)
        sharedPrefChar = getSharedPreferences(CHAR_PREF_NAME, PRIVATE_MODE)
        sharedPrefMount = getSharedPreferences(MOUNT_PREF_NAME, PRIVATE_MODE)
        sharedPrefToken = getSharedPreferences(AUTH_PREF_NAME, PRIVATE_MODE)


        AuthKey.token = sharedPrefToken.getString("TOKEN", "")!!
        AuthKey.timeOfDeath = sharedPrefToken.getLong("DEATH", 0)

        if (AuthKey.token == "" || AuthKey.timeOfDeath < System.currentTimeMillis()) {
            AuthKey.getToken(this)
        }

        if (sharedPrefOption.getBoolean("THEME", false)) {
            setTheme(R.style.HordeTheme)
        } else {
            setTheme(R.style.AllianceTheme)
            val editor = sharedPrefOption.edit()
            editor.putBoolean(OPTION_PREF_NAME, false)
            editor.apply()
        }

        setContentView(R.layout.activity_main)

        createNotificationChannel()

        setupAlarm()


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

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "BreadReminderChannel"
            val description: String = "Channel for Bread notifications"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel("notifyBread", name, importance)
            channel.description = description

            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun setupAlarm() {
        val receiverIntent: Intent = Intent(this, Alarm::class.java)

        if (PendingIntent.getBroadcast(
                this,
                0,
                receiverIntent,
                PendingIntent.FLAG_NO_CREATE
            ) != null
        ) {
            val pendingIntent: PendingIntent =
                PendingIntent.getBroadcast(this, 0, receiverIntent, PendingIntent.FLAG_NO_CREATE)
            val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            Log.v("ALARM", "Cancelling alarm")
        }
        val tz = TimeZone.getDefault()
        val now = Date()
        val offsetFromUtc = tz.getOffset(now.time) / 60000
        val calendar: Calendar = Calendar.getInstance()
        if (!sharedPrefOption.getBoolean("REGION", false))
            calendar.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 7 + (offsetFromUtc / 60))
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
        else
            calendar.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 15 + (offsetFromUtc / 60))
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
        if (calendar.timeInMillis < System.currentTimeMillis()){
            calendar.timeInMillis += 24*60*60*1000
        }

        Log.v("ALARM", "Alarm set at time: " + calendar.toString())
        Log.d("ALARM", "Alarm is not active. Enabling it.")
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, receiverIntent, 0)
        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}