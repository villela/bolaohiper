package com.matheusvillela.bolaohiperweb

import com.matheusvillela.bolaohiperweb.model.globo.GloboScrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit

@Configuration
 class ScrapperConfiguration {
    @Bean
    fun globoScrapper(retrofit: Retrofit): GloboScrapper =
            retrofit.create(GloboScrapper::class.java)
}