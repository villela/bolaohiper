package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.Player
import com.matheusvillela.bolaohiperweb.model.Standing
import org.springframework.data.mongodb.repository.MongoRepository

interface StandingRepository : MongoRepository<Standing, Player>