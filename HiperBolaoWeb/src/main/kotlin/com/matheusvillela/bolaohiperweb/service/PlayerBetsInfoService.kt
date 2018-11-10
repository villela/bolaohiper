package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.BetWithPoints
import com.matheusvillela.bolaohiperweb.model.PlayerBetsInfo
import com.matheusvillela.bolaohiperweb.repository.PlayerBetsInfoRepository
import org.springframework.stereotype.Service


@Service
class PlayerBetsService(private val playerBetsInfoRepository: PlayerBetsInfoRepository,
                        private val scoreService: ScoreService,
                        private val playerService: PlayerService) {
    fun update() {
        val playersBets = playerService.getPlayersBets()
        val betsInfoList = mutableListOf<PlayerBetsInfo>()
        playersBets.forEach { playerWithBets ->
            val list = mutableListOf<BetWithPoints>()
            playerWithBets.bets.forEach { bet ->
                bet.game.score?.let { score ->
                    val points = scoreService.getScoreForBet(bet, score)
                    list.add(BetWithPoints(bet, points))
                }
            }
            betsInfoList.add(PlayerBetsInfo(playerWithBets.player.id, playerWithBets.player,
                    playerWithBets.bets, list))
        }
        playerBetsInfoRepository.saveAll(betsInfoList)
    }

    fun getPlayerBetsInfo(playerId: Int) = playerBetsInfoRepository.findById(playerId)
}