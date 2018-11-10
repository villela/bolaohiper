package com.matheusvillela.bolaohiperweb.loader

import com.matheusvillela.bolaohiperweb.service.GameService
import com.matheusvillela.bolaohiperweb.service.PlayerService
import com.matheusvillela.bolaohiperweb.service.TeamService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component


@Component
class DataLoader(private val gameService: GameService,
                 private val playerService: PlayerService,
                 private val teamService: TeamService) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        teamService.prePop()
        gameService.prePop()
        playerService.prePop()
    }
}