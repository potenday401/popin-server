package kr.co.popin.domain.model.auth.vo

import kr.co.popin.util.UUIDGenerator
import java.io.Serializable

data class AuthTokenId (
    val id: String
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        fun newAuthTokenId() = AuthTokenId(
            UUIDGenerator
                .newUUID()
                .toString()
        )
    }
}