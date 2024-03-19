package kr.co.popin.presentation.user.response

data class UserLoginResponse (
    val accessToken: String,
    val refreshToken: String
)