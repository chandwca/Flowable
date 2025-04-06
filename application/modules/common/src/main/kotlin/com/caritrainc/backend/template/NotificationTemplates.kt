package com.caritrainc.backend.template

fun getWelcomeNotifications(appUrl: String, businessId: String): String {
    return """
    <span>Welcome to Caritra!, the home of professional service businesses. To make your business fully operational in Caritra, complete <a href="${appUrl}/dashboard/${businessId}/Business-Status">business verification</a> & set up a <a href="${appUrl}/dashboard/${businessId}/Bank-Details">bank account</a> for direct deposit of payment, and configure your services to be sold in Caritra Marketplace. Feel free to contact us at <a href="mailto: contact@caritra.com">contact@caritra.com</a> for assistance in setting up.</span>
""".trimIndent()
}