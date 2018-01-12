package com.example.kotlinfastnetworking.application

import android.app.Application
import com.androidnetworking.AndroidNetworking

/**
 * Created by nikhil.jadhav on 01-12-2017.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }
}