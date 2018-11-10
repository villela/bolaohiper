package com.matheusvillela.hiperbolao.model

data class PlayerBetsInfo(val id: Int,
                          val player: Player,
                          val bets: List<Bet>,
                          val betsWithPoints: List<BetWithPoints>)