package kr.co.popin.domain.model.user.vo

import kr.co.popin.util.UUIDGenerator
import java.io.Serializable

data class UserId (
    val id: String
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        fun newUserId() = UserId(
            UUIDGenerator
                .newUUID()
                .toString()
        )
    }
}