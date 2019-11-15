package ru.asv.bmd.base

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.service.VoteService
import java.time.LocalDate

@Import(TestConfig::class)
@RunWith(SpringRunner::class)
@DataMongoTest
class RepositoryTest {

    @Autowired
    lateinit var vs : VoteService

    @Test
    fun testCreate() {
        val createdEntity = vs.create(VoteInfo(LocalDate.now(), LocalDate.now(), "Test Creator", emptyList(), mutableListOf()))
        assertTrue(createdEntity.block() != null)
    }

}