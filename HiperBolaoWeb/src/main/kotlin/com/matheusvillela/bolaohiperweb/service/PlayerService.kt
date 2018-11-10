package com.matheusvillela.bolaohiperweb.service

import com.matheusvillela.bolaohiperweb.model.Bet
import com.matheusvillela.bolaohiperweb.model.Player
import com.matheusvillela.bolaohiperweb.model.PlayerWithBets
import com.matheusvillela.bolaohiperweb.repository.GameRepository
import com.matheusvillela.bolaohiperweb.repository.PlayerWithBetsRepository
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader


@Service
class PlayerService(private val playerWithBetsRepository: PlayerWithBetsRepository,
                    private val gameRepository: GameRepository) {
    fun prePop() {
        playerWithBetsRepository.deleteAll()
        val players = mutableListOf<PlayerWithBets>()
        val resource = ClassPathResource("MONGO_PRE_POP/players.csv")
        val reader = BufferedReader(InputStreamReader(resource.inputStream))
        val lines = reader.readLines()
        val header = lines[0].split(",")
        val gamesMap = gameRepository.findAll().associateBy({ it.id }, { it })
        header.forEachIndexed { i, name ->
            if (i % 4 == 0 && !isNameExcluded(name)) {
                val bets = mutableListOf<Bet>()
                lines.subList(1, lines.size).forEach { line ->
                    val split = line.split(",")
                    val homeScore = split[i].toInt()
                    val awayScore = split[i + 1].toInt()
                    val gameId = split[0].toInt()
                    gamesMap[gameId]?.let {
                        bets.add(Bet(it, homeScore, awayScore))
                    }
                }
                val id = header[i + 3].toInt()
                players.add(PlayerWithBets(id, Player(id, name), bets))
            }
        }
        playerWithBetsRepository.saveAll(players)
    }

    private fun isNameExcluded(name: String): Boolean {
        return name == "Oficial" || name == "Antônio Falcão Junior" || name == "Everton Moura"
    }

    fun update() {
        val list = mutableListOf<PlayerWithBets>()
        val gamesMap = gameRepository.findAll().associateBy({ it.id }, { it })
        playerWithBetsRepository.findAll().forEach { playerWithBets ->
            val bets = mutableListOf<Bet>()
            playerWithBets.bets.forEach { bet ->
                gamesMap[bet.game.id]?.let {
                    bets.add(bet.copy(game = it))
                }
            }
            list.add(PlayerWithBets(playerWithBets.player.id, playerWithBets.player, bets))
        }
        playerWithBetsRepository.saveAll(list)
    }

    fun getPlayerBets(playerId: Int): PlayerWithBets = playerWithBetsRepository.findByPlayerId(playerId)
    fun getPlayersBets(): List<PlayerWithBets> = playerWithBetsRepository.findAll()
}