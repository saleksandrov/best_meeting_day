package ru.asv.bmd.base.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult
import java.time.LocalDate

@Service
@Profile("demo")
class DemoVoteService : VoteService {

    override fun create(vi: VoteInfo): Mono<VoteInfo> {
        val voteInfo = VoteInfo().apply {
            id = "test_id"
            creationDate = LocalDate.now()
            bestDatesForCreator = mutableListOf()
            votes = mutableListOf()
            startDate = LocalDate.now()
            endDate = LocalDate.now()
            creator = ""
        }
        return Mono.just(voteInfo)
    }

    override fun addVote(id: String, vote: Vote): Mono<VoteInfo> {
        return Mono.just(VoteInfo().apply {
            this.id = "test_id"
            creationDate = LocalDate.now()
            bestDatesForCreator = mutableListOf()
            votes = mutableListOf()
            startDate = LocalDate.now()
            endDate = LocalDate.now()
            creator = ""
        })
    }

    override fun getVote(id: String): Mono<VoteInfo> {
        return Mono.just(VoteInfo().apply {
            this.id = "test_id"
            creationDate = LocalDate.now()
            bestDatesForCreator = mutableListOf()
            votes = mutableListOf()
            startDate = LocalDate.now()
            endDate = LocalDate.now()
            creator = ""
        })
    }

    override fun getBestDates(id: String): VoteResult {
        return VoteResult().apply {
            bestDay = LocalDate.now()
            bestDayVoters = mutableListOf()
            bestDayWithCreator = LocalDate.now()
            bestDayWithCreatorVoters = mutableListOf()
        }
    }

}