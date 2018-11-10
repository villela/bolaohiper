package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.Team
import com.matheusvillela.bolaohiperweb.repository.TeamRepository
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class TeamService(private val teamRepository: TeamRepository) {
    fun prePop() {
        teamRepository.deleteAll()
        val resource = ClassPathResource("MONGO_PRE_POP/teams.csv")
        val reader = BufferedReader(InputStreamReader(resource.inputStream))
        val teams = mutableListOf<Team>()
        reader.readLines().forEach {
            val split = it.split(",")
            val team = Team(split[3], split[2])
            teams.add(team)
        }
        teamRepository.saveAll(teams)
    }

    fun getTeams(): List<Team> = teamRepository.findAll()
}