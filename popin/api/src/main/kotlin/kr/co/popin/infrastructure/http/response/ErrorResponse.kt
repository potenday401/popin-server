package kr.co.popin.infrastructure.http.response

import kr.co.popin.infrastructure.http.enums.ErrorResponseCode

class ErrorResponse (
    override val responseCode: ErrorResponseCode = ErrorResponseCode.UNKNOWN,
    override val responseMessage: String? = null,
    override val responseData: Any? = null
) : IResponse