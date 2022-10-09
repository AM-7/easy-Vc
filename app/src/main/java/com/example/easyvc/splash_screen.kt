package com.example.easyvc

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

class splash_screen : AppCompatActivity() {
    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        if(isConnected()==false){
            var builder= AlertDialog.Builder(applicationContext)

            builder.setTitle("No Internet Connection")
            builder.setMessage("Do you want to exit?")

            builder.setPositiveButton("Yes"){ dialogInterface, which->
                finish()
                System.exit(0)
            }

            builder.setNegativeButton("Check WIFI"){ dialogInterface, which->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                finish()
                System.exit(0)

            }

            var alertDialog:AlertDialog=builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }else{
            handler=Handler()
            handler.postDelayed({
                val intent=Intent(applicationContext,signIn::class.java)
                startActivity(intent)
                finish()

            },2000)
        }


    }

    fun isConnected():Boolean{
        var connectivityManager: ConnectivityManager =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var network: NetworkInfo?=connectivityManager.activeNetworkInfo

        if(network!=null){
            if(network.isConnected){
                return true
            }
        }
        return false
    }
}