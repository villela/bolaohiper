package com.matheusvillela.bolaohiperweb.model.globo

import pl.droidsonroids.jspoon.annotation.Selector
import java.util.*

data class GloboPage (@Selector(value = ".card-jogo") var games: List<GloboGame> = ArrayList())