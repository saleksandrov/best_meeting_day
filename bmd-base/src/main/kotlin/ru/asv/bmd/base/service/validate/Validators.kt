package ru.asv.bmd.base.service.validate

import ru.asv.bmd.base.exception.ValidationException
import ru.asv.bmd.base.model.Vote
import ru.asv.bmd.base.model.VoteInfo

fun validateVoteInfo(vi: VoteInfo) {
    if (vi.startDate.isAfter(vi.endDate)) {
        throw ValidationException("Дата начала должна быть раньше даты окончания")
    }
    if (vi.bestDatesForCreator.isEmpty()) {
        throw ValidationException("Не выбраны даты")
    }
    vi.bestDatesForCreator.forEach {
        if (it.isBefore(vi.startDate) || it.isAfter(vi.endDate)) {
            throw ValidationException("Выбранные даты не попадают в указанный диапазон")
        }
    }
    if (vi.creator.isBlank()) {
        throw ValidationException("Введите свое имя")
    }
    if (!(nameRegex() matches vi.creator)) {
        throw ValidationException("Имя может содержать только буквы и цифры")
    }
    if (vi.creator.length > 200) {
        throw ValidationException("Имя превыщает длину 200 символов")
    }
}

private fun nameRegex() = Regex("^[a-zA-ZйцукенгшщзхъфывапролджэёячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЁЯЧСМИТЬБЮ\\s\\d]+$")

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
        throw ValidationException("Не введены даты")
    }
    if (vote.author.isBlank()) {
        throw ValidationException("Введите свое имя")
    }
    if (!(nameRegex() matches vote.author)) {
        throw ValidationException("Имя может содержать только буквы и цифры")
    }
    if (vote.author.length > 200) {
        throw ValidationException("Имя превыщает длину 200 символов")
    }
}

