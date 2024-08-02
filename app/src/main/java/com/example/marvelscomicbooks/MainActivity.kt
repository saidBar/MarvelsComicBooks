package com.example.marvelscomicbooks

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val publicKey = "e63f84093231091d8b8bdd1a1133c347"
    private val privateKey = "9b4eaf50de8f30c8317307e15799b3f611395793"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(5000)
        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchMarvelCharacters()
    }

    private fun fetchMarvelCharacters() {
        val timestamp = Date().time.toString()
        val hash = md5("$timestamp$privateKey$publicKey")


        val call = RetrofitInstance.api.getCharacters(
            apiKey = publicKey,
            timestamp = timestamp,
            hash = hash,
            limit = 30,
            offset = 0
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

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}


