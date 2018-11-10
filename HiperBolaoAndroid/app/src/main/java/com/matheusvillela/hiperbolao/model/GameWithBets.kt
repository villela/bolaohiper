package com.matheusvillela.hiperbolao.model

data class GameWithBets(val gameId: Int,
                        val game: Game,
                        val bets: List<PlayerWithBetAndScore>)