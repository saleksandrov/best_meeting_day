package ru.asv.bmd.base

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.config.*
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.config.IRuntimeConfig
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import ru.asv.bmd.base.service.BaseVoteService


@Configuration
open class TestConfig : AbstractReactiveMongoConfiguration() {

    @Bean
    open fun voteService() = BaseVoteService()

    @Bean
    open fun embeddedMongoRuntimeConfig(): IRuntimeConfig {
        val command = Command.MongoD
        return RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(DownloadConfigBuilder()
                                .defaultsForCommand(command)
                                .build())
                        .build())
                .build()
    }

    @Bean
    open fun mongodConfig() : IMongodConfig {
        return MongodConfigBuilder()
                .version(Version.Main.DEVELOPMENT)
                .net(Net(28000, Network.localhostIsIPv6()))
                .build()
    }

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create("mongodb://localhost:28000")
    }

    override fun getDatabaseName(): String {
        return "bmdbase"
    }

    @Bean
    override fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName())
    }

}