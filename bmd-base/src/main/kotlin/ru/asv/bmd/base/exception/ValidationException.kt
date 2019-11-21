package ru.asv.bmd.base.exception

class ValidationException : RuntimeException {

    constructor(msg: String?) : super(msg)
    constructor(msg: String?, ex: Throwable?) : super(msg, ex)
    constructor(ex: Throwable?) : super(ex)

}