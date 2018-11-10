package com.matheusvillela.bolaohiperweb.model.globo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GloboScrapper {
    @GET("{day}/jogos.ghtml")
    fun getDayGames(@Path(value = "day", encoded = true) day: String): Call<GloboPage>
}

