package ru.asv.bmd.base.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult
import ru.asv.bmd.base.repository.VoteRepository
import java.util.*

@Service
class VoteService {

    @Autowired
    lateinit var vr : VoteRepository

    fun create(vi: VoteInfo) : Mono<VoteInfo> {
        return vr.save(vi)
    }

    fun addVote(id: String, vote: Vote) : Mono<VoteInfo> {
        return vr.findById(id).block().let {
            it.votes.add(vote)
            vr.save(it)
        }
    }

    fun getBestDates(id: String) : VoteResult {
        val bestDates = mutableMapOf<Date, MutableList<String>>()
        vr.findById(id).block().let {vi ->
            vi.votes.forEach {vote ->
                vote.bestDates.forEach{date ->
                    bestDates.getOrElse(date) {
                        mutableListOf()
                    }.add(vote.author)
                }
            }
        }
        // TODO calculate best date

        return VoteResult(Date(), mutableListOf(), Date(), mutableListOf())
    }

}