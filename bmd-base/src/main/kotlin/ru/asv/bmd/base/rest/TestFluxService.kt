package ru.asv.bmd.base.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.TestResult


@RestController
@RequestMapping("/test")
class TestFluxService {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/get"], method = [RequestMethod.GET])
    fun test(): Mono<TestResult> {
        return Mono.just(TestResult("TEST"))
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/getList"], method = [RequestMethod.GET], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun testList(): Flux<TestResult> {
        return Flux.fromArray(arrayOf(TestResult("TEST 1"), TestResult("TEST 2"), TestResult("TEST 3")))
    }

}