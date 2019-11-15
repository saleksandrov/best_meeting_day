package ru.asv.bmd.base.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import ru.asv.bmd.base.repository.VoteRepository


@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = arrayOf(VoteRepository::class))
open class MongoConfig : AbstractReactiveMongoConfiguration() {

    @Value("\${bmd.db.port}")
    private lateinit var port: String

    @Value("\${bmd.db.dbname}")
    private lateinit var dbName: String

    @Value("\${bmd.db.host}")
    private lateinit var host: String


    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create("mongodb://${host}:${port}")
    }

    override fun getDatabaseName(): String {
        return dbName
    }

    @Bean
    override fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName())
    }

}
