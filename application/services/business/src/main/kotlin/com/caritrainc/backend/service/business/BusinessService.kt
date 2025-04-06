package com.caritrainc.backend.service.business

import com.caritrainc.backend.database.collection.app.Business
import com.caritrainc.backend.database.repository.app.BusinessRepository
import com.caritrainc.backend.exception.AppCustomException
import com.caritrainc.backend.exception.AppException
import com.caritrainc.backend.model.UpdateBusinessRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BusinessService {

    @Autowired
    lateinit var businessRepository: BusinessRepository

    fun createBusiness(business: Business): Business {
        return businessRepository.save(business)
    }

    fun getAllBusinesses(pageable: Pageable): Page<Business> {
        return businessRepository.findAll(pageable)
    }

    fun getBusinessById(id: UUID): Business {
        val optionalBusiness = businessRepository.findById(id)
        if (!optionalBusiness.isPresent) {
            throw AppException(exception = AppCustomException.BusinessNotFoundException)
        }
        return optionalBusiness.get()
    }

    fun updateBusiness(id: UUID, updatedBusiness: UpdateBusinessRequest): Business {
        val optionalBusiness = businessRepository.findById(id)
        if (!optionalBusiness.isPresent) {
            throw AppException(exception = AppCustomException.BusinessNotFoundException)
        }
        val business = optionalBusiness.get()
        business.apply {
            name = updatedBusiness.name
            about = updatedBusiness.about
            email = updatedBusiness.email
            phoneNumber = updatedBusiness.phoneNumber
        }
        businessRepository.save(business)
        return business
    }

    fun deleteBusiness(id: UUID) {
        businessRepository.deleteById(id)
    }
}