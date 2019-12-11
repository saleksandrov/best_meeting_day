package ru.asv.bmd.base

import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder
import de.flapdoodle.embed.process.config.IRuntimeConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.asv.bmd.base.service.VoteService


@Configuration
open class TestConfig {

    @Bean
    open fun voteService() = VoteService()

    @Bean
    open fun embeddedMongoRuntimeConfig(): IRuntimeConfig {
        val command = Command.MongoD
        return RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(DownloadConfigBuilder()
                                .defaultsForCommand(command)
                                .downloadPath("file:c:/Users/17540258/Downloads/")
                                .build())
                        .build())
                .build()
    }

}