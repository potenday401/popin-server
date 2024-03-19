package kr.co.popin.application.exceptions

import kr.co.popin.infrastructure.http.enums.ErrorResponseCode

class NotFoundAuthTokenException : RuntimeException(ErrorResponseCode.UNAUTHORIZED.getRealCode())