package com.matheusvillela.hiperbolao.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeAdapter {

    @ToJson
    fun toJson(localDateTime: LocalDateTime): String = FORMATTER.format(localDateTime)

    @FromJson
    fun fromJson(str: String): LocalDateTime = FORMATTER.parse(str, LocalDateTime.FROM)

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }
}
