package com.caritrainc.backend.exception

enum class AppCustomException(val errorMessage: String, val errorCode: Int) {
    UserNotFoundException("User Not Found", 101),
    ImageNotFoundException("Image Not Found", 102),
    BusinessNotFoundException("Business Not Found", 103),
    StaffNotFoundException("Staff Not Found", 104),
}

class AppException(val exception: AppCustomException) : RuntimeException(exception.errorMessage)