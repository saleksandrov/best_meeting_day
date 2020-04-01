package ru.asv.bmd.base.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.asv.bmd.base.service.CoreService

@RestController
@RequestMapping("/adm/base", produces = [MediaType.APPLICATION_JSON_VALUE])
class AdminRestService @Autowired constructor(
        private val coreService: CoreService) {

    @GetMapping("get_tv")
    fun getTotalVotes(): Mono<Long> {
        return coreService.totalVotes()
    }

}