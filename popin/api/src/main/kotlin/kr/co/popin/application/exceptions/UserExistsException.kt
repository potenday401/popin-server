package kr.co.popin.application.exceptions

import kr.co.popin.infrastructure.http.enums.ErrorResponseCode

class UserExistsException : RuntimeException(ErrorResponseCode.DUPLICATE_USER.getRealCode()) {
}