package ru.asv.bmd.base.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MongoConverter
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import ru.asv.bmd.base.model.Sequence
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.repository.VoteRepository


@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = [VoteRepository::class])
@Profile("!demo")
open class MongoConfig : AbstractReactiveMongoConfiguration() {

    @Value("\${spring.data.mongodb.port}")
    private lateinit var port: String

    @Value("\${spring.data.mongodb.database}")
    private lateinit var dbName: String

    @Value("\${spring.data.mongodb.host}")
    private lateinit var host: String

    @Value("\${spring.data.mongodb.username}")
    private lateinit var username: String

    @Value("\${spring.data.mongodb.password}")
    private lateinit var password: String

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create("mongodb://${username}:${password}@${host}:${port}")
    }

    override fun getDatabaseName(): String {
        return dbName
    }

    @Bean
    override fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName())
    }

    /**
     *  Creation of all required indexes
     */
    @Autowired
    lateinit var mongoConverter: MongoConverter

    @Autowired
    lateinit var mongoTemplate: ReactiveMongoTemplate

    @EventListener(classes = [ApplicationReadyEvent::class])
    fun initIndicesAfterStartup() {
        val mappingContext = this.mongoConverter.getMappingContext()
        val resolver = MongoPersistentEntityIndexResolver(mappingContext)
        val indexOpsVi = mongoTemplate.indexOps(VoteInfo::class.java)
        val indexOpsSeq = mongoTemplate.indexOps(Sequence::class.java)
        resolver.resolveIndexFor(VoteInfo::class.java).forEach { indexOpsVi.ensureIndex(it) }
        resolver.resolveIndexFor(Sequence::class.java).forEach { indexOpsSeq.ensureIndex(it) }
    }

}
