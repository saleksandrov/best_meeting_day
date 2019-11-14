package ru.asv.bmd.base.repository

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import ru.asv.bmd.base.model.VoteInfo
import reactor.core.publisher.Flux

@Repository
interface VoteRepository : ReactiveCrudRepository<VoteInfo, String> {

    @Query("{ 'creator': ?0 }")
    fun findByCreator(creator: String): Flux<VoteInfo>

}