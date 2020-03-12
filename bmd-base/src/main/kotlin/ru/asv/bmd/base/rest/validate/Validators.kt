package ru.asv.bmd.base.rest.validate

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo

fun validateVoteInfo(vi: VoteInfo) : Mono<ResponseEntity<Any>>? {
    if (vi.startDate.isAfter(vi.endDate)) {
        return reportBadRequest("Дата начала должна быть раньше даты окончания")
    }
    if (vi.bestDatesForCreator.isEmpty()) {
        return reportBadRequest("Не выбраны даты")
    }
    vi.bestDatesForCreator.forEach {
        if (it.isBefore(vi.startDate) || it.isAfter(vi.endDate)) {
            return reportBadRequest("Выбранные даты не попадают в указанный диапазон")
        }
    }
    if (vi.creator.isBlank()) {
        return reportBadRequest("Введите свое имя")
    }
    if (!(nameRegex() matches vi.creator)) {
        return reportBadRequest("Имя может содержать только буквы и цифры")
    }
    if (vi.creator.length > 200) {
        return reportBadRequest("Имя превыщает длину 200 символов")
    }
    return null
}

private fun nameRegex() = Regex("^[a-zA-Z\\s\\d]+$")

fun validateId(id: String): Mono<ResponseEntity<Any>>? {
    if (id.isBlank()) {
        return reportBadRequest("Id может быть пустым")
    }
    if (id.length > 50) {
        return reportBadRequest("Неверный Id")
    }
    return null
}

fun validateVote(vote: Vote): Mono<ResponseEntity<Any>>? {
    if (vote.bestDates.isEmpty()) {
        return reportBadRequest("Не введены даты")
    }
    if (vote.author.isBlank()) {
        return reportBadRequest("Введите свое имя")
    }
    if (!(nameRegex() matches vote.author)) {
        return reportBadRequest("Имя может содержать только буквы и цифры")
    }
    if (vote.author.length > 200) {
        return reportBadRequest("Имя превыщает длину 200 символов")
    }
    return null
}

fun reportBadRequest(msg: String) =
        Mono.just(ResponseEntity.badRequest().body(msg) as ResponseEntity<Any>)
