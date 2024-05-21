package kr.co.popin.application.exceptions

import kr.co.popin.infrastructure.http.enums.ErrorResponseCode

class NotFoundUserException : RuntimeException(ErrorResponseCode.BAD_REQUEST.getRealCode())
