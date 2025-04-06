package com.caritrainc.backend.service.business

import com.caritrainc.backend.database.collection.app.Staff
import com.caritrainc.backend.database.repository.app.StaffRepository
import com.caritrainc.backend.exception.AppCustomException
import com.caritrainc.backend.exception.AppException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class StaffService {

    @Autowired
    lateinit var staffRepository: StaffRepository

    fun createStaff(staff: Staff): Staff {
        return staffRepository.save(staff)
    }

    fun getAllStaffs(pageable: Pageable): Page<Staff> {
        return staffRepository.findAll(pageable)
    }

    fun getStaffById(id: UUID): Staff {
        val optionalStaff = staffRepository.findById(id)
        if (!optionalStaff.isPresent) {
            throw AppException(exception = AppCustomException.StaffNotFoundException)
        }
        return optionalStaff.get()
    }

    fun updateStaff(id: UUID): Staff {
        val optionalStaff = staffRepository.findById(id)
        if (!optionalStaff.isPresent) {
            throw AppException(exception = AppCustomException.StaffNotFoundException)
        }
        val staff = optionalStaff.get()
        staffRepository.save(staff)
        return staff
    }

    fun deleteStaff(id: UUID) {
        staffRepository.deleteById(id)
    }
}