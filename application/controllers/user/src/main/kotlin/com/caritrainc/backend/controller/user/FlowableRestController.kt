package com.caritrainc.backend.controller.user


import com.caritrainc.backend.model.UpdateUserRequest
import com.caritrainc.backend.service.user.FlowableService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/flowable")
class FlowableRestController(private val flowableService: FlowableService) {

    @GetMapping("/start")
    fun startProcess(): String {
        flowableService.startProcess()
        return "Flowable process started!"
    }


    @PostMapping("/evaluate")
    fun evaluateUser(@RequestBody request: UpdateUserRequest): Map<String, Any> {
        return flowableService.startUserEvaluation(request.name, request.age, request.membershipType,request.status, request.discount)
    }
}
