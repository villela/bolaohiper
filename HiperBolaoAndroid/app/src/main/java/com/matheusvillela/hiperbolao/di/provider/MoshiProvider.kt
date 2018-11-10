package com.matheusvillela.hiperbolao.di.provider

import com.matheusvillela.hiperbolao.util.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Provider

class MoshiProvider @Inject constructor() : Provider<Moshi> {

    override fun get(): Moshi {
        return Moshi.Builder()
                .add(LocalDateTimeAdapter())
                .build()
    }
}