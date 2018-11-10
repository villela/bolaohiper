package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.Game
import org.springframework.data.mongodb.repository.MongoRepository

interface GameRepository : MongoRepository<Game, Int>