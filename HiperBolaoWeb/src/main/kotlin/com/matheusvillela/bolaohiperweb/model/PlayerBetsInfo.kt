package com.matheusvillela.bolaohiperweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players_bet_info")
data class PlayerBetsInfo(@Id val id: Int,
                          val player: Player,
                          val bets: List<Bet>,
                          val betsWithPoints: List<BetWithPoints>)