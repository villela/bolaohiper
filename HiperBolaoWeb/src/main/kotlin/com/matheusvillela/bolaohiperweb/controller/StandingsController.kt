package com.matheusvillela.bolaohiperweb.controller

import com.matheusvillela.bolaohiperweb.model.Standing
import com.matheusvillela.bolaohiperweb.service.StandingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class StandingsController(private val standingService: StandingService) {

    @RequestMapping(value = ["/standings"], method = [RequestMethod.GET])
    fun getStandings(): List<Standing> = standingService.getStandings()
}