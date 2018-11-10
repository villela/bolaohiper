package com.matheusvillela.bolaohiperweb

import com.mongodb.MongoClient
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter


@Configuration
class MongoConfig {
    @Bean
    fun mongoClient(): MongoClient {
        return EmbeddedMongoBuilder()
                .bindIp("127.0.0.1")
                .port(12345)
                .build()
//        return MongoClient("localhost:27017")
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate {
        val template = MongoTemplate(mongoClient, "bolao")
        val mappingMongoConverter = template.converter as MappingMongoConverter
        mappingMongoConverter.setTypeMapper(DefaultMongoTypeMapper(null))
        return template
    }
}