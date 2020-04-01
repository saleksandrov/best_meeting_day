package ru.asv.bmd.base.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Sequence
import ru.asv.bmd.base.model.VOTE_SEQ
import javax.annotation.PostConstruct


interface CoreService {
    fun getNextVal() : Mono<Sequence>
}

@Service
class BaseCoreService @Autowired constructor(
        private val mongoTemplate: ReactiveMongoTemplate) : CoreService {

    private val log = LoggerFactory.getLogger(BaseCoreService::class.java)

    @PostConstruct
    fun init() {
        val query = Query().apply {
            addCriteria(Criteria.where("name").`is`(VOTE_SEQ))
        }
        val update = Update().apply {
            set("name", VOTE_SEQ)
        }
        mongoTemplate.upsert(query, update, Sequence::class.java).subscribe()
    }

    override fun getNextVal() : Mono<Sequence> {
        val query = Query().apply {
            addCriteria(Criteria.where("name").`is`(VOTE_SEQ))
        }
        val update = Update().apply {
            inc("value")
        }

        val seq = mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                Sequence::class.java)
        return seq
    }

}