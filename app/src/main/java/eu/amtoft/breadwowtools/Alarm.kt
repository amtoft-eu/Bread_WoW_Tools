package eu.amtoft.breadwowtools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class Alarm : BroadcastReceiver() {

    private var PRIVATE_MODE = 0
    private val OPTION_PREF_NAME = "OPTIONS"
    private val CHAR_PREF_NAME = "CHARACTERS"
    private val MOUNT_PREF_NAME = "MOUNTS"

    override fun onReceive(context: Context, intent: Intent?) {

        val sharedPrefOption: SharedPreferences = context.getSharedPreferences(OPTION_PREF_NAME, PRIVATE_MODE)
        val sharedPrefChar: SharedPreferences = context.getSharedPreferences(CHAR_PREF_NAME, PRIVATE_MODE)
        val sharedPrefMount: SharedPreferences = context.getSharedPreferences(MOUNT_PREF_NAME, PRIVATE_MODE)


        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> {

            }
            Calendar.WEDNESDAY -> {

            }
            else -> {

            }
        }



    }


    fun notifyDaily(context: Context, intent: Intent?){
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "notifyBread")
            .setSmallIcon(R.drawable.mount_gmod)
            .setContentTitle("Daily reset!")
            .setContentText("Mounts have been reset")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager : NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }

    fun notifyWeekly(context: Context, intent: Intent?){
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "notifyBread")
            .setSmallIcon(R.drawable.mount_gmod)
            .setContentTitle("Weekly reset!")
            .setContentText("Mounts have been reset")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager : NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }

    fun resetDaily(context: Context, intent: Intent?){

    }

    fun resetWeekly(context: Context, intent: Intent?){

    }

}