package eu.amtoft.breadwowtools

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.api.Token


object AuthKey {
    var token = ""
    var timeOfDeath = 0L
    val clientId = "5014d622da2c46d2aa3e720cb7e57b2d"
    val clientSecret = "cbO3Z7Oly8vNzP56SJuuA5ABZdyKS4Ym"

    fun getToken(activity: MainActivity) {
        Log.v("AUTH", "In getToken")

        val queue = Volley.newRequestQueue(activity as MainActivity)
        var url = "https://eu.battle.net/oauth/token"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                Log.v("AUTH", response)
                val gson: Gson = Gson()
                var apiToken = gson.fromJson(response, Token::class.java)
                token = apiToken.access_token
                timeOfDeath = System.currentTimeMillis() + apiToken.expires_in
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