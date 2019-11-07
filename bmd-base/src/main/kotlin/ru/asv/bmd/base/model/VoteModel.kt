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
        val votes: List<Vote>
) {
    @Id
    var id: Int = 0
}

@Document
data class Vote(
    val author: String,
    val bestDates: List<Date>
) {
    @Id
    var id: Int = 0
}