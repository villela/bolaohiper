package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.Game
import com.matheusvillela.bolaohiperweb.repository.GameRepository
import com.matheusvillela.bolaohiperweb.repository.StandingRepository
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class GameService(private val gameRepository: GameRepository,
                  private val teamService: TeamService,
                  private val standingRepository: StandingRepository) {
    fun prePop() {
        val teamsMap = teamService.getTeams().map { e -> e.name to e }.toMap()
        gameRepository.deleteAll()
        val resource = ClassPathResource("MONGO_PRE_POP/games.csv")
        val reader = BufferedReader(InputStreamReader(resource.inputStream))
        val games = mutableListOf<Game>()
        reader.readLines().forEach {
            val split = it.split(",")
            val homeTeam = teamsMap[split[3]]
            val awayTeam = teamsMap[split[6]]
            if (homeTeam != null && awayTeam != null) {
                val game = Game(id = split[0].toInt(),
                        start = createDateFromStr(split[8], split[9]),
                        homeTeam = homeTeam,
                        awayTeam = awayTeam,
                        score = null)
                games.add(game)
            } else {
                println("cant find bolaohiperweb team for: ${split[3]}, ${split[6]} ")
            }
        }
        gameRepository.saveAll(games)
    }

    private fun createDateFromStr(day: String, time: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val zero = if (time.length == 5) "" else "0"
        return LocalDateTime.parse("$day $zero$time", formatter)
    }

    fun getGames(): List<Game> = gameRepository.findAll()

}