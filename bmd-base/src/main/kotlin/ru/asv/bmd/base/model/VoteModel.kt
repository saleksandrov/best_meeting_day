package ru.asv.bmd.base.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class VoteInfo(
        val startDate: Date,
        val endDate: Date,
        val creator: String,
        val bestDates: List<Date>,
        val votes: MutableList<Vote>
) {
    @Id
    var id: String = ""
}

@Document
data class Vote(
    val author: String,
    val bestDates: List<Date>
) {
    @Id
    var id: String = ""
}

data class VoteResult(
    val bestDay: Date,
    val bestDayVoters: MutableList<String>,
    val bestDayWithCreator: Date,
    val bestDayWithCreatorVoters: MutableList<String>
)
