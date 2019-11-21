package ru.asv.bmd.base.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
class VoteInfo {

    @Id
    var id: String? = null

    @JsonFormat(pattern = "dd.MM.yyyy")
    var creationDate: LocalDate = LocalDate.now()

    @JsonFormat(pattern = "dd.MM.yyyy")
    var bestDatesForCreator: MutableList<LocalDate> = mutableListOf()

    var votes: MutableList<Vote> = mutableListOf()

    @JsonFormat(pattern = "dd.MM.yyyy")
    var startDate: LocalDate = LocalDate.now()

    @JsonFormat(pattern = "dd.MM.yyyy")
    var endDate: LocalDate = LocalDate.now()

    var creator: String = ""

}

@Document
class Vote {

    var author: String = ""

    @JsonFormat(pattern = "dd.MM.yyyy")
    var bestDates: MutableList<LocalDate> = mutableListOf()

}

class VoteResult {

    @JsonFormat(pattern = "dd.MM.yyyy")
    var bestDay: LocalDate = LocalDate.now()

    var bestDayVoters: MutableList<String> = mutableListOf()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    var bestDayWithCreator: LocalDate = LocalDate.now()

    var bestDayWithCreatorVoters: MutableList<String> = mutableListOf()

}
