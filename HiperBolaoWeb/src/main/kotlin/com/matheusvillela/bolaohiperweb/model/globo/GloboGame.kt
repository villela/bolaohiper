package com.matheusvillela.bolaohiperweb.model.globo

import pl.droidsonroids.jspoon.annotation.Selector

data class GloboGame @JvmOverloads
constructor(@Selector(".placar-mandante") var homeScore: String? = null,
            @Selector(".placar-visitante") var awayScore: String? = null,
            @Selector(".mandante .nome-abreviado") var homeAbbr: String? = null,
            @Selector(".visitante .nome-abreviado") var awayAbbr: String? = null,
            @Selector(".placar-penaltis-mandante") var homeShootoutScore: String? = null,
            @Selector(".placar-penaltis-visitante") var awayShootoutScore: String? = null,
            @Selector(".com-transmissao") var inProgress: String? = null)