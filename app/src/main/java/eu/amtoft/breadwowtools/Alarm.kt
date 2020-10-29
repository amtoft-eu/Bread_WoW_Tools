package eu.amtoft.breadwowtools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.amtoft.breadwowtools.mounts.Mount
import eu.amtoft.breadwowtools.mounts.MountCollection
import java.util.*

class Alarm : BroadcastReceiver() {

    private var PRIVATE_MODE = 0
    private val OPTION_PREF_NAME = "OPTIONS"
    private val MOUNT_PREF_NAME = "MOUNTS"

    override fun onReceive(context: Context, intent: Intent?) {

        val sharedPrefOption: SharedPreferences = context.getSharedPreferences(OPTION_PREF_NAME, PRIVATE_MODE)

        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> {
                if (sharedPrefOption.getBoolean("REGION", false)) {
                    resetDaily(context)
                    if (sharedPrefOption.getBoolean("DAILY", false))
                        notifyDaily(context)
                }
                else {
                    resetWeekly(context)
                    if (sharedPrefOption.getBoolean("WEEKLY", true))
                        notifyWeekly(context)

                }
            }
            Calendar.WEDNESDAY -> {
                if (!sharedPrefOption.getBoolean("REGION", false)) {
                    resetDaily(context)
                    if (sharedPrefOption.getBoolean("DAILY", false))
                        notifyDaily(context)
                }
                else {
                    resetWeekly(context)
                    if (sharedPrefOption.getBoolean("WEEKLY", true))
                        notifyWeekly(context)

                }
            }
            else -> {
                resetDaily(context)
                if (sharedPrefOption.getBoolean("DAILY", false))
                    notifyDaily(context)
            }
        }



    }


    fun notifyDaily(context: Context){
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "notifyBread")
            .setSmallIcon(R.drawable.mount_gmod)
            .setContentTitle("Daily reset!")
            .setContentText("Mounts have been reset")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager : NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }

    fun notifyWeekly(context: Context){
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "notifyBread")
            .setSmallIcon(R.drawable.mount_gmod)
            .setContentTitle("Weekly reset!")
            .setContentText("Mounts have been reset")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager : NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }

    fun resetDaily(context: Context){
        val sharedPrefMount: SharedPreferences = context.getSharedPreferences(MOUNT_PREF_NAME, PRIVATE_MODE)
        val mountsJson = sharedPrefMount.getString(MOUNT_PREF_NAME, "")
        val gson = Gson()
        if (mountsJson != "") {
            Log.v("MAIN", "Mounts found in shared pref")
            val itemType = object : TypeToken<ArrayList<Mount>>() {}.type
            MountCollection.unknownMounts =
                gson.fromJson<ArrayList<Mount>>(mountsJson, itemType)
        }
        MountCollection.unknownMounts.forEach { mount ->
            if (mount.reset == Reset.DAILY)
                for (i in 0 until mount.checkedList.size){
                    mount.checkedList[i] = false
                }

        }
        MountCollection.saveMountList(context)
    }

    fun resetWeekly(context: Context){
        val sharedPrefMount: SharedPreferences = context.getSharedPreferences(MOUNT_PREF_NAME, PRIVATE_MODE)
        val mountsJson = sharedPrefMount.getString(MOUNT_PREF_NAME, "")
        val gson = Gson()
        if (mountsJson != "") {
            Log.v("MAIN", "Mounts found in shared pref")
            val itemType = object : TypeToken<ArrayList<Mount>>() {}.type
            MountCollection.unknownMounts =
                gson.fromJson<ArrayList<Mount>>(mountsJson, itemType)
        }
        MountCollection.unknownMounts.forEach { mount ->
            for (i in 0 until mount.checkedList.size){
                mount.checkedList[i] = false
            }

        }
        MountCollection.saveMountList(context)
    }

}