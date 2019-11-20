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
class BmdBaseRestService @Autowired constructor(val vs : VoteService) {

    private val log = LoggerFactory.getLogger(BmdBaseRestService::class.java)

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/start"], method = [RequestMethod.POST])
    //fun startVote(serverWebExchange: ServerWebExchange ): Mono<VoteInfo> {
    fun startVote(@RequestBody vi: VoteInfo) : Mono<VoteInfo> {
        log.info("Starting vote ${vi}")
        // TODO add validation
        val createdVoteInfo = vs.create(vi)
        log.info("Vote was created ${createdVoteInfo}")
        return createdVoteInfo
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addVote(@RequestParam id: String, @RequestParam vote: Vote): Mono<VoteInfo> {
        // TODO add validation
        return vs.addVote(id , vote)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/getBestDates"], method = [RequestMethod.GET])
    fun getBestDates(@RequestParam id: String): Mono<VoteResult> {
        // TODO add validation
        return Mono.just(vs.getBestDates(id))
    }


}