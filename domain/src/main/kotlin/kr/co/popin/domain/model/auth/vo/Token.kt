package kr.co.popin.domain.model.auth.vo

import java.io.Serializable

data class Token (
    val token: String
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}