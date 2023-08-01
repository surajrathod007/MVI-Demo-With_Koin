package com.surajrathod.mvidemo

import android.app.Application
import com.surajrathod.mvidemo.koin.activityModule
import com.surajrathod.mvidemo.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(appModule, activityModule)
        }
    }

}