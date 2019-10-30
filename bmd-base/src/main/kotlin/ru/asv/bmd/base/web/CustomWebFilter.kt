package ru.asv.bmd.base.web

import org.springframework.stereotype.Component
import org.springframework.web.server.WebFilterChain
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import reactor.core.publisher.Mono


@Component
class CustomWebFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return if (exchange.request.uri.path == "/") {
            chain.filter(
                    exchange.mutate().request(
                            exchange.request.mutate().path("/ui/index.html").build()
                    ).build()
            )
        } else chain.filter(exchange)

    }

}