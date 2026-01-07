package ru.animbus.ordomentis

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.animbus.ordomentis.di.databaseModule
import ru.animbus.ordomentis.di.uiModule

class OrdoMentisApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@OrdoMentisApp)
            modules(databaseModule, uiModule)
        }
    }
}