package ru.asv.bmd.base.repository

import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.VoteInfo

@Repository
@Profile("!demo")
interface VoteRepository : ReactiveMongoRepository<VoteInfo, String> {

    @Query("{ 'creator': ?0 }")
    fun findByCreator(creator: String): Flux<VoteInfo>

    @Query("{ 'number': ?0 }")
    fun findByNumber(number: Long): Mono<VoteInfo>

}