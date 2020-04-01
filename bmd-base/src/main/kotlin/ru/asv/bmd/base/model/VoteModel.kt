package ru.asv.bmd.base.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
class VoteInfo {

    @Id
    var id: String? = null

    @Indexed
    var number: Long = 0

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

    var description: String = ""
        set(value) {
            value.replace("<", "&lt;")
            value.replace(">", "&gt;")
            field = value
        }
}

@Document
class Vote {

    var author: String = ""

    @JsonFormat(pattern = "dd.MM.yyyy")
    var bestDates: MutableList<LocalDate> = mutableListOf()

}

class VoteResult {

    @JsonFormat(pattern = "dd.MM.yyyy")
    var bestDay: LocalDate? = null

    var bestDayVoters: MutableList<String> = mutableListOf()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    var bestDayWithCreator: LocalDate? = null

    var bestDayWithCreatorVoters: MutableList<String> = mutableListOf()

    var totalVotes: Int = 0

    var creator: String = ""

    var description: String = ""

}

const val VOTE_SEQ = "vote_seq"

@Document
class Sequence {

    @Id
    var id : String? = null

    @Indexed
    var name : String = "none"

    var value : Long = 0

}
