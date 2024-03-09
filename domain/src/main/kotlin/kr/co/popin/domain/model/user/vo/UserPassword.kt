package kr.co.popin.domain.model.user.vo

import java.io.Serializable

data class UserPassword (
    val password: String
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}