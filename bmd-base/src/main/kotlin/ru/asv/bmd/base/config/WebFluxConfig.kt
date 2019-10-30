package ru.asv.bmd.base.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFlux
open class WebFluxConfig : WebFluxConfigurer {

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

        //registry.addResourceHandler("/ui/static/**")
        //        .addResourceLocations("classpath:/ui/public/static/")
        //        .setCacheControl(cacheControl)
        //        .resourceChain(resourceChain)

        registry.addResourceHandler("/ui/**")
                .addResourceLocations("classpath:/ui/public/")
                .setCacheControl(cacheControl)
                .resourceChain(resourceChain)
    }
}