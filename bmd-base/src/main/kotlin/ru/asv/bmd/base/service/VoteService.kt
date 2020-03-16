package ru.asv.bmd.base.service

import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.model.VoteResult

interface VoteService {
    fun create(vi: VoteInfo): Mono<VoteInfo>
    fun addVote(id: String, vote: Vote): Mono<VoteInfo>
    fun getVote(id: String): Mono<VoteInfo>
    fun getBestDates(id: String): Mono<VoteResult>
}