package com.matheusvillela.bolaohiperweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players_with_bets")
data class PlayerWithBets(@Id val id: Int,
                          val player: Player,
                          val bets: List<Bet>)