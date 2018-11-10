package com.matheusvillela.bolaohiperweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "standings")
data class Standing(@Id val player: Player,
                    val position: Int,
                    val positionsEarned: Int,
                    val lastRoundResult: RoundResult)