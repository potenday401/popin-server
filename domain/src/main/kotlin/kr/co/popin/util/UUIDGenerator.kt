package kr.co.popin.util

import com.github.f4b6a3.uuid.alt.GUID
import java.util.*

object UUIDGenerator {
    fun newUUID(): UUID {
        return GUID.v7().toUUID()
    }

    fun newHyphenLessUUID(): String {
        val uuid = newUUID()

        return uuid.toString().replace("-", "")
    }
}