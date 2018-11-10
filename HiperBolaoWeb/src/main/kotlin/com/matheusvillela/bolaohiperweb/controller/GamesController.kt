package com.matheusvillela.bolaohiperweb.controller

import com.matheusvillela.bolaohiperweb.model.Game
import com.matheusvillela.bolaohiperweb.service.GameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GamesController(private val gamesService: GameService ) {

    @GetMapping("/games")
    fun getGames(): List<Game>
            = gamesService.getGames()
}