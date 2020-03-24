package ru.asv.bmd.base

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.service.VoteService
import java.time.LocalDate

@Import(TestConfig::class)
@RunWith(SpringRunner::class)
@DataMongoTest
class RepositoryTest {

    @Autowired
    lateinit var voteService : VoteService

    @Test
    fun testCreateSimple() {
        val vi = buildVoteInfo()
        val voteGuid = createAndCheck(vi)

        val vote1 = Vote().apply {
            author = "Author 1"
            bestDates = mutableListOf(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3))
        }
        val vote2 = Vote().apply {
            author = "Author 2"
            bestDates = mutableListOf(LocalDate.now().plusDays(2))
        }
        if (voteGuid != null) {
            val v1 = voteService.addVote(voteGuid, vote1)
            val v2 = voteService.addVote(voteGuid, vote2)

            StepVerifier.create(v1).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v2).expectNextMatches{ it.votes.size > 0 }.verifyComplete()

            val bestDates = voteService.getBestDates(voteGuid)
            StepVerifier.create(bestDates).expectNextMatches{it.bestDay!!.equals(vote2.bestDates.first())}
            StepVerifier.create(bestDates).expectNextMatches{it.bestDayVoters.containsAll(listOf(vote1.author, vote2.author)) }
        }

    }

    @Test
    fun testCreateFull() {
        val vi = buildVoteInfo()
        val voteGuid = createAndCheck(vi)

        val vote1 = Vote().apply {
            author = "Author 1"
            bestDates = mutableListOf(LocalDate.now().plusDays(3), LocalDate.now().plusDays(4))
        }
        val vote2 = Vote().apply {
            author = "Author 2"
            bestDates = mutableListOf(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), LocalDate.now().plusDays(5) )
        }
        val vote3 = Vote().apply {
            author = "Author 3"
            bestDates = mutableListOf(LocalDate.now().plusDays(2), LocalDate.now().plusDays(4))
        }

        val vote4 = Vote().apply {
            author = "Author 4"
            bestDates = mutableListOf(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5))
        }
        val vote5 = Vote().apply {
            author = "Author 5"
            bestDates = mutableListOf(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5))
        }
        val vote6 = Vote().apply {
            author = "Author 6"
            bestDates = mutableListOf(LocalDate.now().plusDays(3), LocalDate.now().plusDays(6))
        }
        val vote7 = Vote().apply {
            author = "Author 7"
            bestDates = mutableListOf(LocalDate.now().plusDays(2), LocalDate.now().plusDays(6))
        }
        val vote8 = Vote().apply {
            author = "Author 8"
            bestDates = mutableListOf(LocalDate.now().plusDays(4), LocalDate.now().plusDays(6))
        }

        if (voteGuid != null) {
            val v1 = voteService.addVote(voteGuid, vote1)
            val v2 = voteService.addVote(voteGuid, vote2)
            val v3 = voteService.addVote(voteGuid, vote3)
            val v4 = voteService.addVote(voteGuid, vote4)
            val v5 = voteService.addVote(voteGuid, vote5)
            val v6 = voteService.addVote(voteGuid, vote6)
            val v7 = voteService.addVote(voteGuid, vote7)
            val v8 = voteService.addVote(voteGuid, vote8)

            StepVerifier.create(v1).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v2).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v3).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v4).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v5).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v6).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v7).expectNextMatches{ it.votes.size > 0 }.verifyComplete()
            StepVerifier.create(v8).expectNextMatches{ it.votes.size > 0 }.verifyComplete()

            val bestDates = voteService.getBestDates(voteGuid)

            StepVerifier.create(bestDates).expectNextMatches{ it.bestDay!!.equals(vote2.bestDates.first())}.verifyComplete()
            StepVerifier.create(bestDates).expectNextMatches{ it.bestDayVoters.containsAll(listOf(vote2.author, vote3.author)) }.verifyComplete()
            StepVerifier.create(bestDates).expectNextMatches{ it.bestDayWithCreator!!.equals(vote5.bestDates.last())}.verifyComplete()

        }
    }

    private fun createAndCheck(vi: VoteInfo): String? {
        val createdEntity = voteService.create(vi)
        val createVi = createdEntity.block()
        assertTrue(createVi != null)
        val voteGuid = createVi!!.id
        assertTrue(voteGuid != null)
        return voteGuid
    }

    private fun buildVoteInfo(): VoteInfo {
        return VoteInfo().apply {
            creationDate = LocalDate.now()
            startDate = creationDate.plusDays(1)
            endDate = creationDate.plusDays(10)
            creationDate = startDate
            creator = "Some Creator"
            bestDatesForCreator = mutableListOf(LocalDate.now().plusDays(5), LocalDate.now().plusDays(6))
        }
    }

}