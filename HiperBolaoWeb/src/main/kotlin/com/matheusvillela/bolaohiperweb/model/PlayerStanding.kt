package com.matheusvillela.bolaohiperweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "player_standings")
data class PlayerStanding(@Id val playerRoundStart: PlayerRoundStart,
                          val position: Int,
                          val positionsEarned: Int,
                          val lastRoundResult: RoundResult)