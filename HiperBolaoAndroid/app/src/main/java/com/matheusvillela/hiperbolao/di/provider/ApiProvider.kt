package com.matheusvillela.hiperbolao.di.provider

import com.matheusvillela.hiperbolao.BuildConfig
import com.matheusvillela.hiperbolao.shared.Api
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Provider


class ApiProvider @Inject constructor(private val client: OkHttpClient,
                                      private val moshi: Moshi) : Provider<Api> {

    override fun get(): Api {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build().create(Api::class.java)
    }
}