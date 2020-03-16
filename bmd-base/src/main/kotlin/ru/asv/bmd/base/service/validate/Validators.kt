package ru.asv.bmd.base.service.validate

import ru.asv.bmd.base.exception.ValidationException
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo


const val NAME_IS_REQUIRED = "Введите свое имя"
const val NAME_IS_NOT_CORRECT = "Имя может содержать только буквы и цифры"
const val NAME_IS_TOO_LONG = "Имя превышает длину 200 символов"
const val NAME_MAX_LENGTH = 200
const val NO_DATES = "Не выбраны даты"
const val DATE_NOT_IN_DIAPASON = "Выбранные даты не попадают в указанный диапазон"
const val DATE_NOT_CORRECT = "Дата начала должна быть раньше даты окончания"

private fun nameRegex() = Regex("^[a-zA-ZйцукенгшщзхъфывапролджэёячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЁЯЧСМИТЬБЮ\\s\\d]+$")

fun validateVoteInfo(vi: VoteInfo) {

    if (vi.startDate.isAfter(vi.endDate)) {
        throw ValidationException(DATE_NOT_CORRECT)
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

fun validateId(id: String) {

    if (id.isBlank()) {
        throw ValidationException("Id может быть пустым")
    }
    if (id.length > 50) {
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
