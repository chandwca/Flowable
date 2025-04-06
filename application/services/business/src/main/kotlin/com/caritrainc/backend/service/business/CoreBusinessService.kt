package com.caritrainc.backend.service.business

import com.caritrainc.backend.database.collection.app.Business
import com.caritrainc.backend.database.collection.app.Notifications
import com.caritrainc.backend.database.collection.app.NotificationsCount
import com.caritrainc.backend.database.collection.app.Staff
import com.caritrainc.backend.database.model.BusinessLimits
import com.caritrainc.backend.database.repository.app.BusinessRepository
import com.caritrainc.backend.database.repository.app.NotificationRepository
import com.caritrainc.backend.exception.AppCustomException
import com.caritrainc.backend.exception.AppException
import com.caritrainc.backend.model.NewBusinessRequest
import com.caritrainc.backend.model.SequenceType
import com.caritrainc.backend.service.common.NotificationsCountService
import com.caritrainc.backend.service.common.NotificationsService
import com.caritrainc.backend.service.common.SequenceService
import com.caritrainc.backend.template.getWelcomeNotifications
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CoreBusinessService {

    @Autowired
    lateinit var businessService: BusinessService

    @Autowired
    lateinit var staffService: StaffService

    @Autowired
    lateinit var notificationsCountService: NotificationsCountService

    @Autowired
    lateinit var notificationsService: NotificationsService

    @Autowired
    lateinit var sequenceService: SequenceService

    fun createTenantBusiness(businessRequest: NewBusinessRequest, domainBaseUrl: String) {
//        TODO()
//          let operatingUnit, defaultImage, defaultStaffImage;
//
//  try {
//    [operatingUnit, defaultImage, defaultStaffImage] = await Promise.all([
//      OperatingUnits.findById(operatingUnitId),
//      DefaultImages.findOne({ type: "businesslogo" }),
//      DefaultImages.findOne({ type: "staffprofile" }),
//    ]);
//  } catch (error) {
//    console.log(error);
//    return res.status(500).send({
//      error: true,
//      errorMessage: "Internal system error.",
//    });
//  }
//
//  if (!operatingUnit) {
//    return res.status(500).send({
//      error: true,
//      errorMessage: "Internal system error.",
//    });
//  }
//          let requiredData;
//
//  try {
//    requiredData = await Promise.all([
//      Users.findOne({ _id: req.userData.userId }),
//      PricePlans.findOne({
//        isActive: true,
//        agreementType: "STANDARD",
//        operatingUnitId,
//      }),
//    ]);
//  } catch (error) {
//    console.log(error);
//    return res.status(500).send({
//      error: true,
//      errorMessage: "Internal system error.",
//    });
//  }
//
//  const [userRef, pricePlan] = requiredData;
//
//  if (!userRef || !pricePlan) {
//    return res.status(500).send({
//      error: true,
//      errorMessage: "Internal system error.",
//    });
//  }
//
//        let stripeAccountBody = new Object();
//
//        //Create basic account object
//        stripeAccountBody.type = operatingUnit.stripeAccountType;
//        stripeAccountBody.country = operatingUnit.countryCode;
//        stripeAccountBody.default_currency = operatingUnit.currencyCode;
//        stripeAccountBody.email = userRef.email;
//
//        //Create tos acceptance for account
//        stripeAccountBody.tos_acceptance = new Object();
//        stripeAccountBody.tos_acceptance.date = Math.floor(Date.now() / 1000);
//        stripeAccountBody.tos_acceptance.ip = req.ipAdd;
//
//        //Setting requested capabilities
//        stripeAccountBody.capabilities = operatingUnit.stripeCapabilities;
//
//        //Setting payout info
//        stripeAccountBody.settings = operatingUnit.stripeAccountSettings;
//
//        //Setting business description
//        stripeAccountBody.business_profile = operatingUnit.stripeBusinessDescription;
//
//        let stripeAccount;
//        try {
//            stripeAccount = await stripe.accounts.create(stripeAccountBody);
//        } catch (error) {
//            console.log(error);
//            return res.status(500).send({
//                error: true,
//                errorMessage: "Internal system error.",
//            });
//        }

        // Create a new business
        val newBusiness = Business(
            name = businessRequest.name,
            about = businessRequest.about,
            email = businessRequest.email,
            phoneNumber = businessRequest.phoneNumber,

        )
        businessService.createBusiness(newBusiness)

        val currentMemberId = sequenceService.getIncrementString(SequenceType.MEMBER)
        val newStaff = Staff(
            business = newBusiness,
            userId = UUID.randomUUID(),
//            userId = userRef._id,
            isOwner = true,
            role = "Admin",
            isActive = true,
            email = businessRequest.email,
//            email = userRef.email,
//        phoneNumber = userRef.phoneNumber,
//        firstName = userRef.firstName,
            firstName = UUID.randomUUID().toString(),
            lastName = UUID.randomUUID().toString(),
//        lastName = userRef.lastName,
            memberId = currentMemberId,
            profileAccess = true,
            schedulingAccess = true,
            dashboardAccess = true,
            financeAccess = true,
            reportAccess = true,
            checkInAccess = true,
//            image = defaultStaffImage.imageURL,
            image = ""
        )
        staffService.createStaff(newStaff)

        notificationsCountService.addNotificationCount(newBusiness)

        notificationsService.addNotification(
            newBusiness,
            getWelcomeNotifications(domainBaseUrl, newBusiness.id.toString())
        )

        // TODO Email yet to send to user
    }
}