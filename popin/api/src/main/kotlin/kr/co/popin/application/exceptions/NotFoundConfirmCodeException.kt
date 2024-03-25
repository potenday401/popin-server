package kr.co.popin.application.exceptions

import kr.co.popin.infrastructure.http.enums.ErrorResponseCode

class NotFoundConfirmCodeException : RuntimeException(ErrorResponseCode.BAD_REQUEST.getRealCode())
