package kr.co.popin.infrastructure.http.response

import kr.co.popin.infrastructure.http.enums.IResponseCode

interface IResponse {
    val responseCode: IResponseCode
    val responseMessage: String?
    val responseData: Any?
}