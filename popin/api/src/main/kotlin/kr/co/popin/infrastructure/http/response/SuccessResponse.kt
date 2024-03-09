package kr.co.popin.infrastructure.http.response

import kr.co.popin.infrastructure.http.enums.SuccessResponseCode

class SuccessResponse (
    override val responseCode: SuccessResponseCode = SuccessResponseCode.SUCCESS,
    override val responseMessage: String? = null,
    override val responseData: Any? = null
) : IResponse