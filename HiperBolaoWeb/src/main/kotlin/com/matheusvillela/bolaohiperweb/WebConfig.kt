package com.matheusvillela.bolaohiperweb

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EnableWebMvc
@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)
        registry.addResourceHandler("/**")
                .addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
    }

    companion object {
        private val CLASSPATH_RESOURCE_LOCATIONS = arrayOf("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/")
    }

}