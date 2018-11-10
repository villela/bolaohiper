package com.matheusvillela.hiperbolao.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class SleepInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Thread.sleep(500L + Random().nextInt(2000))
        return chain.proceed(chain.request())
    }
}