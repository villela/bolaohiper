package com.matheusvillela.bolaohiperweb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players")
data class Player(@Id val id: Int,
                  val name: String)