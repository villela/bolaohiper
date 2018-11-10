package com.matheusvillela.hiperbolao

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.jakewharton.threetenabp.AndroidThreeTen
import com.matheusvillela.hiperbolao.di.AppModule
import com.matheusvillela.hiperbolao.util.ProductionTree
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator


class BolaoApp : Application() {

    companion object {
        lateinit var instance : BolaoApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
        MemberInjectorRegistryLocator.setRootRegistry(com.matheusvillela.hiperbolao.MemberInjectorRegistry())
        FactoryRegistryLocator.setRootRegistry(com.matheusvillela.hiperbolao.FactoryRegistry())

        AndroidThreeTen.init(this)

        val appScope: Scope = Toothpick.openScope(this)

        appScope.installModules(AppModule(this))
        Toothpick.inject(this, appScope)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
            Timber.plant(ProductionTree())
        }
    }
}