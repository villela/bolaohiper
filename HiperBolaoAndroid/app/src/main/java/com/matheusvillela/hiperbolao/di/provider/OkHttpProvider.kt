package com.matheusvillela.hiperbolao.di.provider

import com.matheusvillela.hiperbolao.BuildConfig
import com.matheusvillela.hiperbolao.util.SleepInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider
class OkHttpProvider @Inject constructor(private val loggingInterceptor: HttpLoggingInterceptor) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(SleepInterceptor())
        }
        return builder.build()
    }
}
