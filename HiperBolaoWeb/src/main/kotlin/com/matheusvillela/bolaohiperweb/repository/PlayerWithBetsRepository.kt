package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.PlayerWithBets
import org.springframework.data.mongodb.repository.MongoRepository

interface PlayerWithBetsRepository : MongoRepository<PlayerWithBets, Int> {
    fun findByPlayerId(playerId : Int) : PlayerWithBets
}