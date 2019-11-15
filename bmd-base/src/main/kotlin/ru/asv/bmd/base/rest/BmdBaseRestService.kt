package ru.asv.bmd.base.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult
import ru.asv.bmd.base.service.VoteService

@RestController
@RequestMapping("/vote")
class BmdBaseRestService @Autowired constructor(val vs : VoteService) {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/start"], method = [RequestMethod.POST])
    fun startVote(@RequestParam vi: VoteInfo): Mono<VoteInfo> {
        // TODO add validation
        return vs.create(vi)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addVote(@RequestParam id: String, @RequestParam vote: Vote): Mono<VoteInfo> {
        // TODO add validation
        return vs.addVote(id , vote)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/getBestDates"], method = [RequestMethod.GET])
    fun getBestDates(@RequestParam id: String): VoteResult {
        // TODO add validation
        return vs.getBestDates(id)
    }


}