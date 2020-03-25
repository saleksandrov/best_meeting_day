package ru.asv.bmd.base.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.asv.bmd.base.exception.ValidationException
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.service.VoteService
import java.util.function.Function

@Suppress("UNCHECKED_CAST")
@RestController
@RequestMapping("/vote", produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = [ "http://localhost:3000", "http://localhost:4200" ])
class BmdBaseRestService @Autowired constructor(val vs : VoteService) {

    private val log = LoggerFactory.getLogger(BmdBaseRestService::class.java)

    @PostMapping(value = ["/start"])
    fun startVote(@RequestBody vi: VoteInfo) : Mono<ResponseEntity<Any>> {
        log.info("Starting vote ${vi}")
        return invokeOrThrow {
            val createdVoteInfo = vs.create(vi)
            log.info("Vote was created ${createdVoteInfo}")

            createdVoteInfo.map { ResponseEntity.ok().body(it) as ResponseEntity<Any>}
        }
    }

    @PostMapping(value = ["/add/{id}"])
    fun addVote(@PathVariable id: String, @RequestBody vote: Vote): Mono<ResponseEntity<Any>> {
        return invokeOrThrow {
            vs.addVote(id , vote)
                    .map{ ResponseEntity.ok().body("OK") as ResponseEntity<Any> }
                    .onErrorResume(
                            object : Function<Throwable, Mono<ResponseEntity<Any>>> {
                                override fun apply(t: Throwable): Mono<ResponseEntity<Any>> {
                                    return reportBadRequest(t.message!!)
                                }
                            }
                    )
        }
    }

    @GetMapping(value = ["/getBestDates/{id}"])
    fun getBestDates(@PathVariable id: String): Mono<ResponseEntity<Any>> {
        return invokeOrThrow { Mono.just(ResponseEntity.ok().body(vs.getBestDates(id)) as ResponseEntity<Any>) }
    }

    @GetMapping(value = ["/get/{id}"])
    fun getVote(@PathVariable id: String): Mono<ResponseEntity<Any>> {
        return invokeOrThrow { Mono.just(ResponseEntity.ok().body(vs.getVote(id)) as ResponseEntity<Any>) }
    }

    fun invokeOrThrow(invokeLogic: () -> Mono<ResponseEntity<Any>>): Mono<ResponseEntity<Any>> {
        return try {
            invokeLogic()
        } catch (ve: ValidationException) {
            reportBadRequest(ve.message!!)
        } catch (e: Exception) {
            log.error("Ошибка вызова сервиса", e)
            reportInternalError("Ошибка вызова сервиса")
        }
    }

    fun reportBadRequest(msg: String) =
            Mono.just(ResponseEntity.badRequest().body(msg) as ResponseEntity<Any>)

    fun reportInternalError(msg: String) =
            Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg) as ResponseEntity<Any>)

}