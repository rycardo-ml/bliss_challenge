package com.example.blisstest

import android.app.Application

class BlissApplication: Application() {

    companion object {
        lateinit var instance: BlissApplication


    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
    }
}