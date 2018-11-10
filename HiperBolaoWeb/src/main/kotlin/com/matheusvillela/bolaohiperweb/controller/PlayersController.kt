package com.matheusvillela.bolaohiperweb.controller

import com.matheusvillela.bolaohiperweb.model.PlayerBetsInfo
import com.matheusvillela.bolaohiperweb.model.PlayerStanding
import com.matheusvillela.bolaohiperweb.service.PlayerBetsService
import com.matheusvillela.bolaohiperweb.service.StandingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayersController(private val standingService: StandingService,
                        private val playerBetsService: PlayerBetsService) {

    @GetMapping("/player/standings/{playerId}")
    fun getPlayerStandings(@PathVariable playerId: Int): List<PlayerStanding> = standingService.getStandingsForPlayerId(playerId)

    @GetMapping("/player/bets/{playerId}")
    fun getPlayerBetsInfo(@PathVariable playerId: Int): PlayerBetsInfo =
            playerBetsService.getPlayerBetsInfo(playerId).get()
}