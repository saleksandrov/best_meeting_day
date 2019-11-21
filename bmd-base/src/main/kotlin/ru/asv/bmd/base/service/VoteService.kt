package ru.asv.bmd.base.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult
import ru.asv.bmd.base.repository.VoteRepository
import java.time.LocalDate

@Service
class VoteService {

    private val log = LoggerFactory.getLogger(VoteService::class.java)

    @Autowired
    lateinit var vr: VoteRepository

    fun create(vi: VoteInfo): Mono<VoteInfo> {
        return vr.save(vi).
                doOnSuccess { log.info("The vote was created. Id=${it.id}") }.
                doOnError { ex -> log.error("Cannot save vote to DB ", ex)  }
    }

    fun addVote(id: String, vote: Vote): Mono<VoteInfo> {
        return vr.findById(id).flatMap { vi ->
            vi.votes.add(vote)
            vr.save(vi)
        }
    }

    fun getBestDates(id: String): VoteResult {
        val authorsResultMap = mutableMapOf<LocalDate, MutableList<String>>()
        vr.findById(id).doOnSuccess { vi ->
            vi.votes.forEach { vote ->
                vote.bestDates.forEach { date ->
                    authorsResultMap.getOrElse(date) {
                        mutableListOf()
                    }.add(vote.author)
                }
            }
            log.info("Found author dates ${authorsResultMap.size} ${authorsResultMap}")
        }.subscribe()

        val maxResult = authorsResultMap.maxBy { it.value.size }
        log.info("Max result ${maxResult}")

        return VoteResult().apply {
            bestDay = maxResult!!.key
            bestDayVoters = maxResult.value
        }
    }

}