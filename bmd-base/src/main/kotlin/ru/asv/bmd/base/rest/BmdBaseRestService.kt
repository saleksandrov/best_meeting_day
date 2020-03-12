package ru.asv.bmd.base.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.rest.validate.reportBadRequest
import ru.asv.bmd.base.rest.validate.validateId
import ru.asv.bmd.base.rest.validate.validateVote
import ru.asv.bmd.base.rest.validate.validateVoteInfo
import ru.asv.bmd.base.service.VoteService

@Suppress("UNCHECKED_CAST")
@RestController
@RequestMapping("/vote", produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = [ "http://localhost:3000", "http://localhost:4200" ])
class BmdBaseRestService @Autowired constructor(val vs : VoteService) {

    private val log = LoggerFactory.getLogger(BmdBaseRestService::class.java)

    @PostMapping(value = ["/start"])
    fun startVote(@RequestBody vi: VoteInfo) : Mono<ResponseEntity<Any>> {
        log.info("Starting vote ${vi}")
        return validateVoteInfo(vi) ?: kotlin.run {
            val createdVoteInfo = vs.create(vi)
            log.info("Vote was created ${createdVoteInfo}")

            createdVoteInfo.map { ResponseEntity.ok().body(it) as ResponseEntity<Any>}
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = ["/add/{id}"])
    fun addVote(@PathVariable id: String, @RequestBody vote: Vote): Mono<ResponseEntity<Any>> {
        var validateMessage = validateId(id)
        validateMessage = validateMessage ?: validateVote(vote)
        val voteInfo = vs.getVote(id).block()
        vote.bestDates.forEach{
            if (voteInfo!!.startDate.isAfter(it) ||
                    voteInfo.endDate.isBefore(it)) {
                return reportBadRequest("Выбранные даты не попадают в указанный диапазон")
            }
        }

        return validateMessage ?: kotlin.run {

            vs.addVote(id , vote)
            Mono.just(ResponseEntity.ok().body(vote) as ResponseEntity<Any>)
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/getBestDates/{id}"])
    fun getBestDates(@PathVariable id: String): Mono<ResponseEntity<Any>> {
        return validateId(id) ?: Mono.just(ResponseEntity.ok().body(vs.getBestDates(id)) as ResponseEntity<Any>)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/get/{id}"])
    fun getVote(@PathVariable id: String): Mono<ResponseEntity<Any>> {
        return validateId(id) ?: Mono.just(ResponseEntity.ok().body(vs.getVote(id)) as ResponseEntity<Any>)
    }

}