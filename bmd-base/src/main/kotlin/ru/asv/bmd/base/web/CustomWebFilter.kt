package ru.asv.bmd.base.web

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
class CustomWebFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return if (exchange.request.uri.path == "/" ||
                exchange.request.uri.path.startsWith("/addvote") ||
                exchange.request.uri.path.startsWith("/viewresult") ||
                exchange.request.uri.path.startsWith("/createvote")) {
            chain.filter(
                    exchange.mutate().request(
                            exchange.request.mutate().path("/index.html").build()
                    ).build()
            )
        } else chain.filter(exchange)

    }

}