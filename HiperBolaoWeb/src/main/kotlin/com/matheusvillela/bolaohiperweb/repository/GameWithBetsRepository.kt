package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.GameWithBets
import org.springframework.data.mongodb.repository.MongoRepository

interface GameWithBetsRepository : MongoRepository<GameWithBets, Int> {
    fun findByGameId(id: Int): GameWithBets
}