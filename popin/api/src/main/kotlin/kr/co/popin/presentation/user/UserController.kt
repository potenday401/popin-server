package kr.co.popin.presentation.user

import kr.co.popin.application.user.UserService
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.user.request.UserDuplicateCheckRequest
import kr.co.popin.presentation.user.request.UserLoginRequest
import kr.co.popin.presentation.user.request.UserSignUpRequest
import kr.co.popin.presentation.user.response.UserLoginResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @PostMapping("/duplicate-check")
    fun userDuplicateCheck(
        @RequestBody request: UserDuplicateCheckRequest
    ): SuccessResponse {
        userService.emailDuplicateCheck(request.email)

        return SuccessResponse()
    }
}