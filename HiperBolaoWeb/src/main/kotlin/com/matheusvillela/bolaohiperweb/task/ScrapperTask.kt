package com.matheusvillela.bolaohiperweb.task

import com.matheusvillela.bolaohiperweb.service.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScrapperTask(private val scoreService: ScoreService,
                   private val standingService: StandingService,
                   private val gameWithBetsService: GameWithBetsService,
                   private val playerService: PlayerService,
                   private val playerBetsService: PlayerBetsService) {
    @Scheduled(fixedRate = 20000, initialDelay = 5000)
    fun loop() {
        scoreService.updateScores()
        playerService.update()
        standingService.updateStandings()
        playerBetsService.update()
        gameWithBetsService.update()
    }
}