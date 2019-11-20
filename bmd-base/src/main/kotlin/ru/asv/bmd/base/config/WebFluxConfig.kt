package ru.asv.bmd.base.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFlux
open class WebFluxConfig : WebFluxConfigurer {

    @Bean
    open fun jackson2JsonEncoder(mapper: ObjectMapper): Jackson2JsonEncoder {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        return Jackson2JsonEncoder(mapper)
    }

    @Bean
    open fun jackson2JsonDecoder(mapper: ObjectMapper): Jackson2JsonDecoder {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        return Jackson2JsonDecoder(mapper)
    }

    @Bean
    open fun webFluxConfigurer(encoder: Jackson2JsonEncoder, decoder: Jackson2JsonDecoder): WebFluxConfigurer {
        return object : WebFluxConfigurer {
            override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer?) {
                configurer!!.defaultCodecs().jackson2JsonEncoder(encoder)
                configurer.defaultCodecs().jackson2JsonDecoder(decoder)
            }
        }
    }

    /*@Bean
    open fun indexRouter(): RouterFunction<ServerResponse> {
        val redirectToIndex =
                ServerResponse
                        .temporaryRedirect(URI("/ui/index.html"))
                        .build()

        return router {
            GET("/") {
                redirectToIndex
            }
        }
    }*/

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val cacheControl = CacheControl.noCache()
        val resourceChain = false

        registry.addResourceHandler("/ui/**")
                .addResourceLocations("classpath:/ui/public/")
                .setCacheControl(cacheControl)
                .resourceChain(resourceChain)

        //registry.addResourceHandler("swagger-ui.html")
        //        .addResourceLocations("classpath:/META-INF/resources/");

        //registry.addResourceHandler("/webjars/**")
        //        .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}