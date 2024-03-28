package kr.co.popin.infrastructure.config.docs.springdoc.response

data class SpringdocExampleResponse(
    val responseCode: String,
    val responseMessage: String,
    val responseData: Any
) {
    constructor(
        responseCode: String
    ) : this(
        responseCode = responseCode,
        responseMessage = "null",
        responseData = "null"
    )
}