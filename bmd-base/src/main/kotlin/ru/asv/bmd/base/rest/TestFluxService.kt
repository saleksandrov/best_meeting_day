package ru.asv.bmd.base.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/test")
class TestFluxService {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/get"], method = [RequestMethod.GET])
    @ResponseBody
    fun test(): Mono<String> {
        return Mono.just("TEST")
    }

}