package eu.amtoft.breadwowtools

import android.app.Activity
import android.util.Base64
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.api.Token
import java.time.*
import java.util.*
import kotlin.collections.HashMap


object AuthKey {
    var token = ""
    var timeOfDeath = 0L
    val clientId = "5014d622da2c46d2aa3e720cb7e57b2d"
    val clientSecret = "cbO3Z7Oly8vNzP56SJuuA5ABZdyKS4Ym"

    fun getToken(activity: Activity) {
        Log.v("AUTH", "In getToken")

        val queue = Volley.newRequestQueue(activity)
        var url = "https://eu.battle.net/oauth/token"
        val stringRequest = object: StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                val gson: Gson = Gson()
                var apiToken = gson.fromJson(response, Token::class.java)
                token = apiToken.access_token
                timeOfDeath = (System.currentTimeMillis() + (apiToken.expires_in * 1000)) - 60000
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Log.v("AUTH", "Got new token that expires at: " + LocalDateTime.ofEpochSecond(timeOfDeath / 1000, 0, OffsetDateTime.now().offset))
                }
                activity.getSharedPreferences("TOKEN_DATA", 0).edit().putString("TOKEN", token).putLong("DEATH", timeOfDeath).apply()
            },
            Response.ErrorListener {
                Log.v("AUTH", "POST didn't work! " + it.message)
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("grant_type", "client_credentials")
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val params = HashMap<String, String>()
                val creds = String.format("%s:%s", clientId, clientSecret)
                val auth = "Basic " + Base64.encodeToString(creds.toByteArray(), Base64.NO_WRAP)
                params["Authorization"] = auth
                return params
            }
        }

        queue.add(stringRequest)

    }
}