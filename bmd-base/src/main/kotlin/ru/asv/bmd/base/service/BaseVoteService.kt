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
import java.time.LocalDate

@Service
@Profile("!demo")
class BaseVoteService : VoteService {

    private val log = LoggerFactory.getLogger(BaseVoteService::class.java)

    @Autowired
    lateinit var vr: VoteRepository

    override fun create(vi: VoteInfo): Mono<VoteInfo> {
        return vr.save(vi).
                doOnSuccess { log.info("The vote was created. Id=${it.id}") }.
                doOnError { ex -> log.error("Cannot save vote to DB ", ex)  }
    }

    override fun addVote(id: String, vote: Vote): Mono<VoteInfo> {
        // Sync block
        //var vi = vr.findById(id).block()
        //vi.votes.add(vote)
        //vr.save(vi).subscribe()

        // Async block
        return vr.findById(id).flatMap { vi ->
            vi.votes.add(vote)
            vr.save(vi)
        }

    }

    override fun getVote(id: String): Mono<VoteInfo>  {
        return vr.findById(id)
    }

    override fun getBestDates(id: String): VoteResult {
        val authorsResultMap = mutableMapOf<LocalDate, MutableList<String>>()
        val withCreatorResultMap = mutableMapOf<LocalDate, MutableList<String>>()
        vr.findById(id).doOnSuccess { vi ->
            val creatorDatesMap = vi.bestDatesForCreator.map { Pair(it, "") }.toMap()
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
            log.info("Found author dates ${authorsResultMap.size} ${authorsResultMap}")
        }.block()

        val maxResult = authorsResultMap.maxBy { it.value.size }
        log.info("Max result ${maxResult}")

        val maxCreatorResult = withCreatorResultMap.maxBy { it.value.size }
        log.info("Max result ${maxCreatorResult}")

        return VoteResult().apply {
            bestDay = maxResult?.key
            bestDayVoters = maxResult?.value ?: mutableListOf()
            bestDayWithCreator = maxCreatorResult?.key
            bestDayWithCreatorVoters = maxCreatorResult?.value ?: mutableListOf()
        }
    }

}