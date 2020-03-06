package ru.asv.bmd.base.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult
import ru.asv.bmd.base.service.VoteService

@RestController
@RequestMapping("/vote", produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = [ "http://localhost:3000", "http://localhost:4200" ])
class BmdBaseRestService @Autowired constructor(val vs : VoteService) {

    private val log = LoggerFactory.getLogger(BmdBaseRestService::class.java)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = ["/start"])
    fun startVote(@RequestBody vi: VoteInfo) : Mono<VoteInfo> {
        log.info("Starting vote ${vi}")
        // TODO add validation
        val createdVoteInfo = vs.create(vi)
        log.info("Vote was created ${createdVoteInfo}")
        return createdVoteInfo
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = ["/add/{id}"])
    fun addVote(@PathVariable id: String, @RequestBody vote: Vote): Mono<Vote> {
        // TODO add validation
        vs.addVote(id , vote)
        return Mono.just(vote)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/getBestDates/{id}"])
    fun getBestDates(@PathVariable id: String): Mono<VoteResult> {
        // TODO add validation
        return Mono.just(vs.getBestDates(id))
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ["/get/{id}"])
    fun getVote(@PathVariable id: String): Mono<VoteInfo> {
        // TODO add validation
        return vs.getVote(id)
    }

}