package com.matheusvillela.bolaohiperweb.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: LocalDateTime, gen: JsonGenerator, sp: SerializerProvider) {
        gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}