package ru.asv.bmd.base.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.asv.bmd.base.model.VoteInfo
import ru.asv.bmd.base.repository.VoteRepository

@Service
class VoteService {

    @Autowired
    lateinit var vr : VoteRepository

    fun insert(vi: VoteInfo) {
        vr.save(vi).subscribe()
    }

}