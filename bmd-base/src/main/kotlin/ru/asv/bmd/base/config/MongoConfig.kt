package ru.asv.bmd.base.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import ru.asv.bmd.base.repository.VoteRepository


@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = arrayOf(VoteRepository::class))
@Profile("!demo")
open class MongoConfig : AbstractReactiveMongoConfiguration() {

    @Value("\${bmd.db.port}")
    private lateinit var port: String

    @Value("\${bmd.db.dbname}")
    private lateinit var dbName: String

    @Value("\${bmd.db.host}")
    private lateinit var host: String

    @Value("\${bmd.db.username}")
    private lateinit var username: String

    @Value("\${bmd.db.password}")
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

}
