package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.*
import com.matheusvillela.bolaohiperweb.repository.GameRepository
import com.matheusvillela.bolaohiperweb.repository.PlayerStandingRepository
import com.matheusvillela.bolaohiperweb.repository.PlayerWithBetsRepository
import com.matheusvillela.bolaohiperweb.repository.StandingRepository
import org.springframework.stereotype.Service

@Service
class StandingService(private val playerStandingRepository: PlayerStandingRepository,
                      private val standingRepository: StandingRepository,
                      private val gameRepository: GameRepository,
                      private val playerWithBetsRepository: PlayerWithBetsRepository,
                      private val scoreService: ScoreService) {

    fun getStandingsForPlayerId(playerId: Int): List<PlayerStanding> = playerStandingRepository.findByPlayerRoundStartPlayerId(playerId)

    fun getStandings(): List<Standing> = standingRepository.findAll()

    fun updateStandings() {
        val players = playerWithBetsRepository.findAll()
        val games = gameRepository.findAll().filter { it.score != null }
        val playerStandings = createPlayersStandings(players, games)
        val sortedByDescending = playerStandings.groupBy { it.playerRoundStart.roundStart }
                .values.sortedByDescending { it.first().playerRoundStart.roundStart }
        standingRepository.saveAll(sortedByDescending.first().map {
            Standing(it.playerRoundStart.player, it.position, it.positionsEarned, it.lastRoundResult)
        })
        playerStandingRepository.saveAll(playerStandings)
        updatePlayersBetsWithPoints()
    }

    fun createPlayersStandings(players: List<PlayerWithBets>, games: List<Game>): List<PlayerStanding> {
        val standings = mutableListOf<PlayerStanding>()
        val groupedGames = games.groupBy { it.start }.values.sortedBy { it.first().start }
        val lastPlayersPosition = hashMapOf<Int, Int>()
        val lastPlayersPoints = hashMapOf<Int, PlayerResult>()
        for (i in 1..groupedGames.size) {
            val itGames = groupedGames[i - 1]
            for (player in players) {
                val playerBetsMap = player.bets.associateBy({ it.game.id }, { it })
                var points = lastPlayersPoints[player.id]?.result?.points ?: 0
                val betsWithPoints = mutableListOf<BetWithPoints>()
                for (game in itGames) {
                    val bet = playerBetsMap[game.id]
                    val score = game.score
                    if (bet != null && score != null) {
                        val scoreForBet = scoreService.getScoreForBet(bet, score)
                        betsWithPoints.add(BetWithPoints(bet, scoreForBet))
                        points += scoreForBet
                    }
                }
                val result = RoundResult(itGames.first().start, points, betsWithPoints)
                lastPlayersPoints[player.id] = PlayerResult(result, player.player)
            }
            val groupedPlayers = lastPlayersPoints.values.groupBy { it.result.points }.values.sortedByDescending { it.first().result.points }
            var currentPosition = 1
            for (j in 0 until groupedPlayers.size) {
                val playerList = groupedPlayers[j]
                for (player in playerList) {
                    val roundStart = PlayerRoundStart(player.player, itGames.first().start)
                    val lastRoundResult = lastPlayersPoints[player.player.id]?.result ?: continue
                    val lastPlayerPosition = lastPlayersPosition[player.player.id]
                    lastPlayersPosition[player.player.id] = currentPosition
                    val positionsEarned = if (lastPlayerPosition == null) 0 else lastPlayerPosition - currentPosition
                    val playerStanding = PlayerStanding(roundStart, currentPosition, positionsEarned, lastRoundResult)
                    standings.add(playerStanding)
                }
                currentPosition += playerList.size
            }
        }
        return standings
    }

    private data class PlayerResult(val result: RoundResult, val player: Player)

    fun getPlayerBetsWithPoints(playerId: Int): List<BetWithPoints> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updatePlayersBetsWithPoints() {

    }
}