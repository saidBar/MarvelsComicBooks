package com.example.marvelscomicbooks

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import org.apache.commons.codec.digest.DigestUtils

class MainActivity : AppCompatActivity() {
    private val publicKey = "e63f84093231091d8b8bdd1a1133c347"
    private val privateKey = "9b4eaf50de8f30c8317307e15799b3f611395793"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (isNetworkAvailable()) {
            fetchMarvelCharacters()
        } else{
            Log.e("NetworkError", "No internet connection")
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

    private fun fetchMarvelCharacters() {
        val timestamp = Date().time.toString()
        val hash = md5(timestamp, privateKey, publicKey)


        val call = RetrofitInstance.api.getCharacters(
            timestamp = timestamp,
            apiKey = publicKey,
            hash = hash,
            limit = 5,
        )

        call.enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(
                call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>
            ) {
                if (response.isSuccessful) {
                    val characterList = response.body()?.data?.results ?: emptyList()
                    characterList.forEach {
                        Log.d(
                            "MarvelCharacter",
                            "Name: ${it.name}, Thumbnail: ${it.thumbnail!!.fullPath}"
                        )
                    }
                } else {
                    Log.e("MarvelAPI", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                Log.e("MarvelAPI", "Error fetching characters", t)
            }
        })
    }

    private fun md5(ts: String, privateKey: String, publicKey: String): String {
        val input = "$ts$privateKey$publicKey"
        return DigestUtils.md5Hex(input)
    }
}


