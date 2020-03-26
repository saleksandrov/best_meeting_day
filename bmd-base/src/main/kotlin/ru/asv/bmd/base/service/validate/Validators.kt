package ru.asv.bmd.base.service.validate

import ru.asv.bmd.base.exception.ValidationException
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo
import java.time.Duration
import java.time.LocalDate


const val NAME_IS_REQUIRED = "Введите свое имя"
const val NAME_IS_NOT_CORRECT = "Имя может содержать только буквы и цифры"
const val NAME_IS_TOO_LONG = "Максимальная длина имени 50 символов"
const val NAME_MAX_LENGTH = 50
const val NO_DATES = "Не выбраны даты"
const val DATE_NOT_IN_DIAPASON = "Выбранные даты не попадают в указанный диапазон"
const val DATE_NOT_CORRECT = "Дата начала должна быть раньше даты окончания"
const val DATE_DIAPASON_NOT_CORRECT = "Диапазон дат слишком большой или слишком маленький (менее 3 дней)"
const val DATES_IS_NOT_IN_DIAPASON = "Выбранные даты не попадают в указанный диапазон"

private fun nameRegex() = Regex("^[a-zA-ZйцукенгшщзхъфывапролджэёячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЁЯЧСМИТЬБЮ\\s\\d]+$")
private fun idRegex() = Regex("^[a-zA-Z\\d-]+$")

fun validateVoteInfo(vi: VoteInfo) {

    if (vi.startDate.isAfter(vi.endDate)) {
        throw ValidationException(DATE_NOT_CORRECT)
    }
    val duration = Duration.between(vi.startDate.atStartOfDay(), vi.endDate.atStartOfDay())
    if (duration.toDays() < 3 || duration.toDays() > 90) {
        throw ValidationException(DATE_DIAPASON_NOT_CORRECT)
    }

    if (vi.bestDatesForCreator.isEmpty()) {
        throw ValidationException(NO_DATES)
    }
    vi.bestDatesForCreator.forEach {
        if (it.isBefore(vi.startDate) || it.isAfter(vi.endDate)) {
            throw ValidationException(DATE_NOT_IN_DIAPASON)
        }
    }
    if (vi.creator.isBlank()) {
        throw ValidationException(NAME_IS_REQUIRED)
    }
    if (!(nameRegex() matches vi.creator)) {
        throw ValidationException(NAME_IS_NOT_CORRECT)
    }
    if (vi.creator.length > NAME_MAX_LENGTH) {
        throw ValidationException(NAME_IS_TOO_LONG)
    }

}

fun validateDateDiapason(vi: VoteInfo, it: LocalDate) {
    if (vi.startDate.isAfter(it) ||
            vi.endDate.isBefore(it)) {
        throw ValidationException(DATES_IS_NOT_IN_DIAPASON)
    }
}

fun validateId(id: String) {

    if (id.isBlank()) {
        throw ValidationException("Id не может быть пустым")
    }
    if (id.length > 50 || !(idRegex() matches id))  {
        throw ValidationException("Неверный Id")
    }

}

fun validateVote(vote: Vote) {

    if (vote.bestDates.isEmpty()) {
        throw ValidationException(NO_DATES)
    }
    if (vote.author.isBlank()) {
        throw ValidationException(NAME_IS_REQUIRED)
    }
    if (!(nameRegex() matches vote.author)) {
        throw ValidationException(NAME_IS_NOT_CORRECT)
    }
    if (vote.author.length > NAME_MAX_LENGTH) {
        throw ValidationException(NAME_IS_TOO_LONG)
    }

}
