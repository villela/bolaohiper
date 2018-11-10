package com.matheusvillela.bolaohiperweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "game_with_bets")
data class GameWithBets(@Id val gameId : Int,
                        val game: Game,
                        val bets: List<PlayerWithBetAndScore>)