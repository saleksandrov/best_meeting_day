package ru.asv.bmd.base.repository

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.asv.bmd.base.model.VoteInfo

@Repository
interface VoteRepository : ReactiveMongoRepository<VoteInfo, String> {

    @Query("{ 'creator': ?0 }")
    fun findByCreator(creator: String): Flux<VoteInfo>

}