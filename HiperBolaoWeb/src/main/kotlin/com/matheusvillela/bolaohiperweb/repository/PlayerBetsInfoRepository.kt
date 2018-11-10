package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.PlayerBetsInfo
import org.springframework.data.mongodb.repository.MongoRepository

interface PlayerBetsInfoRepository : MongoRepository<PlayerBetsInfo, Int>