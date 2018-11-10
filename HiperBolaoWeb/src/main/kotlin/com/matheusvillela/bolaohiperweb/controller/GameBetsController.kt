package com.matheusvillela.bolaohiperweb.controller

import com.matheusvillela.bolaohiperweb.model.GameWithBets
import com.matheusvillela.bolaohiperweb.service.GameWithBetsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GameBetsController(private val gameWithBetsService: GameWithBetsService) {

    @GetMapping("/game/{gameId}/bets")
    fun getGameBets(@PathVariable gameId: Int): GameWithBets = gameWithBetsService.getGameWithBets(gameId)
}