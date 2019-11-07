package ru.asv.bmd.base

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import ru.asv.bmd.base.config.MongoConfig
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.service.VoteService
import java.util.*

//@Import(MongoConfig::class)
//@SpringBootTest
@RunWith(SpringRunner::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [BaseApplication::class])
@DataMongoTest
class RepositoryTest {

    @Autowired
    lateinit var vs : VoteService

    @Test
    fun tstCreate() {
        vs.insert(VoteInfo(Date(), Date(), "Test Creator", emptyList(), emptyList()))
    }
}