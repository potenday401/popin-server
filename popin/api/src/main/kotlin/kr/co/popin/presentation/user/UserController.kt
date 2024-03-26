package kr.co.popin.presentation.user

import kr.co.popin.application.user.UserService
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.user.request.*
import kr.co.popin.presentation.user.response.SentEmailConfirmCodeResponse
import kr.co.popin.presentation.user.response.UserLoginResponse
import kr.co.popin.presentation.user.response.VerifyConfirmCodeResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {
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

    @PostMapping("/logout")
    fun logoutUser(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String
    ): SuccessResponse {
        userService.logout(accessToken)

        return SuccessResponse()
    }

    @PostMapping("/duplicate-check")
    fun userDuplicateCheck(
        @RequestBody request: UserDuplicateCheckRequest
    ): SuccessResponse {
        userService.emailDuplicateCheck(request.email)

        return SuccessResponse()
    }

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