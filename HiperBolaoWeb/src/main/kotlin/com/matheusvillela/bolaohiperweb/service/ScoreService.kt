package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.Bet
import com.matheusvillela.bolaohiperweb.model.Game
import com.matheusvillela.bolaohiperweb.model.Score
import com.matheusvillela.bolaohiperweb.model.globo.GloboScrapper
import com.matheusvillela.bolaohiperweb.repository.GameRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ScoreService(private val gameRepository: GameRepository,
                   private val globoScrapper: GloboScrapper) {
    fun updateScores() {
        val games = gameRepository.findAll()
        val groups = games.groupBy { it.start.toLocalDate() }
        for ((day, list) in groups) {
            val needsUpdate = list.any { game ->
                val now = LocalDateTime.now()
                (game.start.isBefore(now) && (game.start.dayOfYear == now.dayOfYear && game.score?.homeScore == game.score?.awayScore))
                ||
                game.score?.finished != true
                        && (game.start.isBefore(now))
            }
            if (needsUpdate) {
                updateDayInfo(day, list)
            }
        }
    }

    private fun updateDayInfo(day: LocalDate, list: List<Game>) {
        val regex = "[^A-Za-z0-9 ]".toRegex()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val result = globoScrapper.getDayGames(day.format(formatter)).execute().body()
        result?.games?.forEach { game ->
            list.forEach { savedGame ->
                val finished = game.inProgress != "ACOMPANHE EM TEMPO REAL"
                val homeScore = game.homeScore?.toIntOrNull()
                val awayScore = game.awayScore?.toIntOrNull()
                val homeShootoutScore = game.homeShootoutScore?.let{regex.replace(it, "").toIntOrNull()}
                val awayShootoutScore = game.awayShootoutScore?.let{regex.replace(it, "").toIntOrNull()}
                if (homeScore != null && awayScore != null) {
                    val totalHomeScore =  homeScore + (homeShootoutScore ?: 0)
                    val totalAwayScore =  awayScore + (awayShootoutScore ?: 0)
                    val (firstScore, secondScore) = if (game.homeAbbr == savedGame.homeTeam.id
                            && game.awayAbbr == savedGame.awayTeam.id) {
                        (totalHomeScore to totalAwayScore)
                    } else if (game.awayAbbr == savedGame.homeTeam.id
                            && game.homeAbbr == savedGame.awayTeam.id) {
                        (totalAwayScore to totalHomeScore)
                    } else {
                        (null to null)
                    }
                    val updatedGame = if(firstScore != null && secondScore != null)
                        savedGame.copy(score = Score(finished, firstScore, secondScore)) else null
                    updatedGame?.let { gameRepository.save(it) }
                }
            }
        }
    }

    fun getScoreForBet(bet: Bet, score: Score): Int {
        var points = 0
        val betHomeAdvantage = bet.homeScore - bet.awayScore
        val scoreHomeAdvantage = score.homeScore - score.awayScore
        if (betHomeAdvantage == scoreHomeAdvantage ||
                (betHomeAdvantage > 0 && scoreHomeAdvantage > 0) ||
                (betHomeAdvantage < 0 && scoreHomeAdvantage < 0)) {
            points += 5
            if (bet.homeScore == score.homeScore) {
                points += 5
            }
            if (bet.awayScore == score.awayScore) {
                points += 5
            }
        } else {
            if (bet.homeScore == score.homeScore) {
                points++
            }
            if (bet.awayScore == score.awayScore) {
                points++
            }
        }
        return points
    }
}