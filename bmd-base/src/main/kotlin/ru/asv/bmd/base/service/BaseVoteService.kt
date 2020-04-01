package ru.asv.bmd.base.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult
import ru.asv.bmd.base.repository.VoteRepository
import ru.asv.bmd.base.service.validate.validateDateDiapason
import ru.asv.bmd.base.service.validate.validateId
import ru.asv.bmd.base.service.validate.validateVote
import ru.asv.bmd.base.service.validate.validateVoteInfo
import java.time.LocalDate
import java.util.*

@Service
@Profile("!demo")
class BaseVoteService : VoteService {

    private val log = LoggerFactory.getLogger(BaseVoteService::class.java)

    @Autowired
    lateinit var vr: VoteRepository

    @Autowired
    lateinit var coreService: CoreService

    override fun create(vi: VoteInfo): Mono<VoteInfo> {
        validateVoteInfo(vi)
        return coreService.getNextVal().doOnSuccess {
            vi.number = it.value
        }.flatMap {
            vr.save(vi)
                    .doOnSuccess { log.info("The vote was created. Id=${it.id}") }
                    .doOnError { ex -> log.error("Cannot save vote to DB ", ex) }
        }
    }

    override fun addVote(id: String, vote: Vote): Mono<VoteInfo> {
        validateId(id)
        validateVote(vote)

        return vr.findById(id).flatMap { vi ->
            vote.bestDates.forEach {
                validateDateDiapason(vi, it)
            }
            vi.votes.add(vote)
            vr.save(vi)
        }

    }

    override fun getVote(id: String): Mono<VoteInfo> {
        validateId(id)
        return vr.findById(id)
    }

    override fun getBestDates(id: String): Mono<VoteResult> {
        validateId(id)
        val authorsResultMap = TreeMap<LocalDate, MutableList<String>>()
        val withCreatorResultMap = TreeMap<LocalDate, MutableList<String>>()
        var creatorDatesMap = emptyMap<LocalDate, String>()
        return vr.findById(id).doOnSuccess { vi ->
              creatorDatesMap = vi.bestDatesForCreator.map { Pair(it, "") }.toMap()
              vi.votes.forEach { vote ->
                vote.bestDates.forEach { date ->
                    authorsResultMap.getOrPut(date) {
                        mutableListOf()
                    }.add(vote.author)
                    if (creatorDatesMap.containsKey(date)) {
                        withCreatorResultMap.getOrPut(date) {
                            mutableListOf()
                        }.add(vote.author)
                    }
                }
            }

        }.flatMap { vi ->
            log.info("Found author dates ${authorsResultMap.size} ${authorsResultMap}")

            val maxResult = authorsResultMap.maxBy { it.value.size }
            log.info("Max result ${maxResult}")

            val maxCreatorResult = withCreatorResultMap.maxBy { it.value.size }
            log.info("Max result ${maxCreatorResult}")

            // add creator to result map
            maxCreatorResult?.key?.let {
                maxCreatorResult.value.add(vi.creator)
            }
            maxResult?.key?.let {
                if (creatorDatesMap.containsKey(maxResult.key)) {
                    maxResult.value.add(vi.creator)
                }
            }

            Mono.just(
                VoteResult().apply {
                    bestDay = maxResult?.key
                    bestDayVoters = maxResult?.value ?: mutableListOf()
                    bestDayWithCreator = maxCreatorResult?.key
                    bestDayWithCreatorVoters = maxCreatorResult?.value ?: mutableListOf()
                    totalVotes = vi.votes.size
                    creator = vi.creator
                    description = vi.description
                }
            )
        }
    }

}