package com.appstr.quickfeeds

import android.app.Application
import com.appstr.quickfeeds.network.QFNetwork

class QuickFeedsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupGlobals()
    }

    private fun setupGlobals(){
        QFNetwork.getInstance(this)
    }
}