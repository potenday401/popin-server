package kr.co.popin.infrastructure.config.docs.springdoc.annotations

import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import java.lang.annotation.Inherited

import kotlin.annotation.AnnotationRetention
import kotlin.annotation.AnnotationTarget

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiSuccessResponseCode(val value: SuccessResponseCode)

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponseCode(val value: ErrorResponseCode)

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class ApiResponseCodes (
    val success: Array<ApiSuccessResponseCode> = [],
    val error: Array<ApiErrorResponseCode> = []
)