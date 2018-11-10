package com.matheusvillela.hiperbolao.di

import android.app.Application
import com.matheusvillela.hiperbolao.di.provider.ApiProvider
import com.matheusvillela.hiperbolao.di.provider.HttpLoggingProvider
import com.matheusvillela.hiperbolao.di.provider.MoshiProvider
import com.matheusvillela.hiperbolao.di.provider.OkHttpProvider
import com.matheusvillela.hiperbolao.shared.Api
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.smoothie.module.SmoothieApplicationModule

class AppModule(application: Application) : SmoothieApplicationModule(application) {
    init {
        this.bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).singletonInScope()
        this.bind(HttpLoggingInterceptor::class.java).toProvider(HttpLoggingProvider::class.java)
        this.bind(Api::class.java).toProvider(ApiProvider::class.java).singletonInScope()
        this.bind(Moshi::class.java).toProvider(MoshiProvider::class.java).singletonInScope()
    }
}