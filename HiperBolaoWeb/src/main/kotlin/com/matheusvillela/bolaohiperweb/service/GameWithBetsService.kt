package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.GameWithBets
import com.matheusvillela.bolaohiperweb.model.PlayerWithBetAndScore
import com.matheusvillela.bolaohiperweb.repository.GameRepository
import com.matheusvillela.bolaohiperweb.repository.GameWithBetsRepository
import com.matheusvillela.bolaohiperweb.repository.PlayerStandingRepository
import com.matheusvillela.bolaohiperweb.repository.PlayerWithBetsRepository
import org.springframework.stereotype.Service

@Service
class GameWithBetsService(private val playerStandingRepository: PlayerStandingRepository,
                          private val gameWithBetsRepository: GameWithBetsRepository,
                          private val playerWithBetsRepository: PlayerWithBetsRepository,
                          private val gameRepository: GameRepository) {

    fun update() {
        val playerStandingList = playerStandingRepository.findAll()
        val gameResultsMap = hashMapOf<Int, MutableList<PlayerWithBetAndScore>>()
        val allGames = gameRepository.findAll()
        val gameMap = allGames.associateBy({ it.id }, { it })
        val playerPositionMap = hashMapOf<Int, Int>()
        for (playerStanding in playerStandingList) {
            for (result in playerStanding.lastRoundResult.bets) {
                val bets = gameResultsMap[result.bet.game.id] ?: mutableListOf()
                gameResultsMap[result.bet.game.id] = bets
                val playerWithBet = PlayerWithBetAndScore(playerStanding.playerRoundStart.player, result.bet, result.points)
                bets.add(playerWithBet)
            }
            playerPositionMap[playerStanding.playerRoundStart.player.id] = playerStanding.position
        }
        val gamesWithoutResult = allGames.filter { it.score == null }
        val playersWithBets = playerWithBetsRepository.findAll()
        for (game in gamesWithoutResult) {
            for (playerWithBet in playersWithBets) {
                val playerBet = playerWithBet.bets.first { it.game.id == game.id }
                val bets = gameResultsMap[game.id] ?: mutableListOf()
                gameResultsMap[game.id] = bets
                val playerWithBetAndScore = PlayerWithBetAndScore(playerWithBet.player, playerBet)
                bets.add(playerWithBetAndScore)
            }
        }
        val gamesWithBets = mutableListOf<GameWithBets>()
        for (entry in gameResultsMap) {
            val game = gameMap[entry.key] ?: continue
            if (game.score == null) {
                entry.value.sortBy { playerPositionMap[it.player.id] }
            }
            val gameWithBets = GameWithBets(entry.key, game, entry.value)
            gamesWithBets.add(gameWithBets)
        }
        gameWithBetsRepository.saveAll(gamesWithBets)
    }

    fun getGameWithBets(gameId: Int): GameWithBets {
        return gameWithBetsRepository.findByGameId(gameId)
    }

}