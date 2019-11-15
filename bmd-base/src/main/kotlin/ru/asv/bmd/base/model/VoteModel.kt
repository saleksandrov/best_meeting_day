package ru.asv.bmd.base.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class VoteInfo(
        @JsonFormat(pattern = "dd-MM-yyyy") val startDate: LocalDate,
        @JsonFormat(pattern = "dd-MM-yyyy") val endDate: LocalDate,
        val creator: String,
        @JsonFormat(pattern = "dd-MM-yyyy") val bestDates: List<LocalDate>,
        val votes: MutableList<Vote>
) {
    @Id
    var id: String = ""

    @JsonFormat(pattern = "dd-MM-yyyy")
    val creationDate: LocalDate = LocalDate.now()
}

@Document
data class Vote(
    val author: String,
    @JsonFormat(pattern = "dd-MM-yyyy") val bestDates: List<LocalDate>
) {
    @Id
    var id: String = ""
}

data class VoteResult(
    @JsonFormat(pattern = "dd-MM-yyyy")  val bestDay: LocalDate,
    val bestDayVoters: MutableList<String>,
    @JsonFormat(pattern = "dd-MM-yyyy") val bestDayWithCreator: LocalDate,
    val bestDayWithCreatorVoters: MutableList<String>
)