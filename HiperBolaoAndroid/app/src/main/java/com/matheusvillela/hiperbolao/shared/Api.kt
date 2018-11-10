package com.matheusvillela.hiperbolao.shared

import com.matheusvillela.hiperbolao.model.Game
import com.matheusvillela.hiperbolao.model.GameWithBets
import com.matheusvillela.hiperbolao.model.PlayerBetsInfo
import com.matheusvillela.hiperbolao.model.Standing
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("standings")
    fun standings(): Single<List<Standing>>

    @GET("games")
    fun games(): Single<List<Game>>

    @GET("/game/{gameId}/bets")
    fun gameWithBets(@Path(value = "gameId", encoded = true) gameId: Int): Single<GameWithBets>

    @GET("/player/bets/{playerId}")
    fun playerBetsInfo(@Path(value = "playerId", encoded = true) playerId: Int): Single<PlayerBetsInfo>
}