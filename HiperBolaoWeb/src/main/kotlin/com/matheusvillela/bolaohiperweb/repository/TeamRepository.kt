package com.matheusvillela.bolaohiperweb.repository

import com.matheusvillela.bolaohiperweb.model.Team
import org.springframework.data.mongodb.repository.MongoRepository

interface TeamRepository : MongoRepository<Team, String>