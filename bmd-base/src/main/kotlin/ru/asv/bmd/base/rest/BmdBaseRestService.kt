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
import ru.asv.bmd.base.model.VoteResult
import ru.asv.bmd.base.service.VoteService

@RestController
@RequestMapping("/vote", produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = [ "http://localhost:3000", "http://localhost:4200" ])
class BmdBaseRestService @Autowired constructor(val vs : VoteService) {

    private val log = LoggerFactory.getLogger(BmdBaseRestService::class.java)

    @PostMapping(value = ["/start"])
    fun startVote(@RequestBody vi: VoteInfo) : Mono<ResponseEntity<Any>> {
        log.info("Starting vote ${vi}")
        return validate(vi) ?: kotlin.run {
            val createdVoteInfo = vs.create(vi)
            log.info("Vote was created ${createdVoteInfo}")

            createdVoteInfo.map { ResponseEntity.ok().body(it) as ResponseEntity<Any>}
        }

    }

    private fun reportBadRequest(msg: String) =
            Mono.just(ResponseEntity.badRequest().body(msg) as ResponseEntity<Any>)

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

    private fun validate(vi: VoteInfo) : Mono<ResponseEntity<Any>>? {
        if (vi.startDate.isAfter(vi.endDate)) {
            return reportBadRequest("Дата начала должна быть раньше даты окончания")
        }
        if (vi.bestDatesForCreator.isEmpty()) {
            return reportBadRequest("Не выбраны даты")
        }
        vi.bestDatesForCreator.forEach{
            if (it.isBefore(vi.startDate) || it.isAfter(vi.endDate)) {
                return reportBadRequest("Выбранные даты не попадают в указанный диапазон")
            }
        }
        if (vi.creator.isBlank()) {
            return reportBadRequest("Введите свое имя")
        }
        return null
    }

}