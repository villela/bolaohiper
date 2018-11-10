package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.PlayerRoundStart
import com.matheusvillela.bolaohiperweb.model.PlayerStanding
import org.springframework.data.mongodb.repository.MongoRepository

interface PlayerStandingRepository : MongoRepository<PlayerStanding, PlayerRoundStart> {
    fun findByPlayerRoundStartPlayerId(id : Int) : List<PlayerStanding>
}