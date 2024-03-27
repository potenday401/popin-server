package kr.co.popin.presentation.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.popin.application.user.UserService
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiErrorResponseCode
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiSuccessResponseCode
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.user.request.*
import kr.co.popin.presentation.user.response.SentEmailConfirmCodeResponse
import kr.co.popin.presentation.user.response.UserLoginResponse
import kr.co.popin.presentation.user.response.VerifyConfirmCodeResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@Tag(name = "User")
@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {
    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.INVALID_EMAIL),
            ApiErrorResponseCode(ErrorResponseCode.INVALID_PASSWORD),
            ApiErrorResponseCode(ErrorResponseCode.DUPLICATE_USER)
        ]
    )
    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    fun signUpUser(
        @RequestBody request: UserSignUpRequest
    ): SuccessResponse {
        userService.registerUser(
            email = request.email,
            password = request.password
        )

        return SuccessResponse()
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.INVALID_EMAIL),
            ApiErrorResponseCode(ErrorResponseCode.INVALID_PASSWORD)
        ]
    )
    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun loginUser(
        @RequestBody request: UserLoginRequest
    ): SuccessResponse {
        val result = userService.login(
            email = request.email,
            password = request.password
        )

        return SuccessResponse(
            responseData = UserLoginResponse(
                accessToken = result.accessToken,
                refreshToken = result.refreshToken
            )
        )
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.UNAUTHORIZED)
        ]
    )
    @Operation(
        summary = "로그아웃",
        description = """
            - 활성화된 모든 토큰을 만료 시킵니다.
        """
    )
    @PostMapping("/logout")
    fun logoutUser(
        @Parameter(hidden = true)
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        accessToken: String
    ): SuccessResponse {
        userService.logout(accessToken)

        return SuccessResponse()
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.INVALID_EMAIL),
            ApiErrorResponseCode(ErrorResponseCode.DUPLICATE_USER)
        ]
    )
    @Operation(
        summary = "유저 중복 체크",
        description = """
            - 해당 이메일로 가입한 유저가 존재하는지 확인함
        """
    )
    @PostMapping("/duplicate-check")
    fun userDuplicateCheck(
        @RequestBody request: UserDuplicateCheckRequest
    ): SuccessResponse {
        userService.emailDuplicateCheck(request.email)

        return SuccessResponse()
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.INVALID_EMAIL),
            ApiErrorResponseCode(ErrorResponseCode.DUPLICATE_USER)
        ]
    )
    @Operation(
        summary = "이메일에 인증 코드 발송",
        description = """
            - 같은 이메일로 하루 5회만 요청 가능
            - 재 요청 시 이미 존재하는 인증 코드는 모두 만료 처리함
            - 유효 기간 10분
        """
    )
    @PostMapping("/send/email/confirm-code")
    fun sendConfirmCode(
        @RequestBody request: SendConfirmCodeRequest
    ): SuccessResponse {
        val result = userService.sendConfirmCodeMail(request.email)

        return SuccessResponse(
            responseData = SentEmailConfirmCodeResponse(
                limit = result.limit,
                toDaySendCount = result.toDaySendCount
            )
        )
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.INVALID_EMAIL),
            ApiErrorResponseCode(ErrorResponseCode.BAD_REQUEST)
        ]
    )
    @Operation(
        summary = "이메일로 받은 인증 코드 검증",
        description = """
            - 한 번 인증 요청 하면 해당 인증 코드는 만료됨
        """
    )
    @PostMapping("/verify/email/confirm-code")
    fun verifyConfirmCode(
        @RequestBody request: VerifyConfirmCodeRequest
    ): SuccessResponse {
        val result = userService.verifyConfirmCode(
            email = request.email,
            authCode = request.confirmCode
        )

        return SuccessResponse(
            responseData = VerifyConfirmCodeResponse(
                limit = result.limit,
                toDaySendCount = result.toDaySendCount
            )
        )
    }

}