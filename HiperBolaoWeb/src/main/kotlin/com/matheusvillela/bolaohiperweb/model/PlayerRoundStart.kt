package com.matheusvillela.bolaohiperweb.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.matheusvillela.bolaohiperweb.util.LocalDateTimeSerializer
import java.time.LocalDateTime


data class PlayerRoundStart(val player: Player,
                            @field:JsonSerialize(using = LocalDateTimeSerializer::class)
                            val roundStart: LocalDateTime)